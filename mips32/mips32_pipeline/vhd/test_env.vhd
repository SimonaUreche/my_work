--Nexys4
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity test_env is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (7 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0)
           );
end test_env;

architecture Behavioral of test_env is

signal funtSeg: std_logic_vector (5 downto 0);
signal saSeg, rtSeg, rdSeg, rWASeg: std_logic_vector (4 downto 0);
signal jumpAdress2: std_logic_vector (3 downto 0);
signal jumpAdress1: std_logic_vector (27 downto 0);
signal en: std_logic := '0';
signal output, InstructionSeg, PCSeg, rd1Seg, rd2Seg, finalMuxSeg, ext_immSeg, branchAdressSeg, aluResSeg, aluResOutSeg, memDataSeg, jumpAdressFinal : std_logic_vector(31 downto 0);
signal RedDstSeg, ExtOpSeg, ALUSrcSeg, BranchSeg, JumpSeg, MemWriteSeg, MemtoRegSeg, RegWriteSeg, zeroSeg, PCSrcFinal,Br_neSeg, Branch1, Branch2: std_logic;
signal ALUOpSeg: std_logic_vector(2 downto 0);

-------------------------------------semnale pipeline---------------------------------------
----------------------------signal IF/ID----------------------------------------------------
signal Instruction_IF_ID: std_logic_vector(31 downto 0);
signal PCp4_IF_ID : std_logic_vector(31 downto 0);

----------------------------signal ID/EX----------------------------------------------------
signal RegDst_ID_EX: std_logic;
signal AluSrc_ID_EX: std_logic;
signal Branch_ID_EX: std_logic;
signal AluOp_ID_EX: std_logic_vector(2 downto 0);
signal MemWrite_ID_EX: std_logic;
signal Mem2Reg_ID_EX: std_logic;
signal RegWrite_ID_EX: std_logic;
signal BNE_ID_EX: std_logic;
signal RD1_ID_EX: std_logic_vector(31 downto 0);
signal RD2_ID_EX: std_logic_vector(31 downto 0);
signal Ext_Imm_ID_EX: std_logic_vector(31 downto 0);
signal funct_ID_EX: std_logic_vector(5 downto 0);
signal sa_ID_EX: std_logic_vector(4 downto 0);                                                  
signal Rd_ID_EX: std_logic_vector(4 downto 0);
signal Rt_ID_EX: std_logic_vector(4 downto 0);
signal Pcp4_ID_EX: std_logic_vector(31 downto 0);

----------------------------signal EX/MEM----------------------------------------------------
signal Branch_EX_MEM : std_logic;
signal MemWrite_EX_MEM: std_logic;
signal Mem2Reg_EX_MEM: std_logic;
signal RegWrite_EX_MEM: std_logic;
signal Zero_EX_MEM: std_logic;
signal BNE_EX_MEM: std_logic;
signal BrAdress_EX_MEM: std_logic_vector(31 downto 0);
signal ALURes_EX_MEM: std_logic_vector(31 downto 0);
signal RD2_EX_MEM: std_logic_vector(31 downto 0);
signal WA_EX_MEM: std_logic_vector(4 downto 0);

----------------------------signal MEM/WB----------------------------------------------------
signal RegWrite_MEM_WB  : std_logic;
signal Mem2Reg_MEM_WB: std_logic;
signal ALURes_MEM_WB : std_logic_vector(31 downto 0);
signal MemData_MEM_WB: std_logic_vector(31 downto 0);
signal WA_MEM_WB: std_logic_vector(4 downto 0);


component MPG is
    Port ( 
    clk : in STD_LOGIC;
    btn: in STD_LOGIC;
    enable : out STD_LOGIC
    );
end component;

component SSD is
    Port ( clk : in STD_LOGIC;
           digits : in STD_LOGIC_VECTOR(31 downto 0);
           an : out STD_LOGIC_VECTOR(7 downto 0);
           cat : out STD_LOGIC_VECTOR(6 downto 0));
end component;

component IFetch is
    Port ( clk : in STD_LOGIC;
           Jump : in STD_LOGIC;
           JumpAddress : in STD_LOGIC_VECTOR (31 downto 0);
           PCSrc : in STD_LOGIC;
           BranchAddress : in STD_LOGIC_VECTOR (31 downto 0);
           en : in STD_LOGIC;
           rst : in STD_LOGIC;
           Instruction : out STD_LOGIC_VECTOR (31 downto 0);
           PC : out STD_LOGIC_VECTOR (31 downto 0));
end component;

component ID is
    Port ( clk: in STD_LOGIC;
           RegWrite : in STD_LOGIC;
           Instr : in STD_LOGIC_VECTOR (25 downto 0);
           Wa : in  STD_LOGIC_VECTOR(4 downto 0);
           en : in STD_LOGIC;
           ExtOp : in STD_LOGIC;
           rd1 : out STD_LOGIC_VECTOR (31 downto 0);
           rd2 : out STD_LOGIC_VECTOR (31 downto 0);
           wd : in STD_LOGIC_VECTOR (31 downto 0);
           ext_imm : out STD_LOGIC_VECTOR (31 downto 0);
           funt : out STD_LOGIC_VECTOR (5 downto 0);
           sa : out STD_LOGIC_VECTOR (4 downto 0);
           rt : out STD_LOGIC_VECTOR (4 downto 0);
           rd : out STD_LOGIC_VECTOR (4 downto 0)
           );
end component;

component UC is
    Port ( Instr : in STD_LOGIC_VECTOR (5 downto 0);
           RedDst : out STD_LOGIC;
           ExtOp : out STD_LOGIC;
           ALUSrc : out STD_LOGIC;
           Branch : out STD_LOGIC;
           Jump : out STD_LOGIC;
           ALUOp : out STD_LOGIC_VECTOR(2 downto 0);
           MemWrite : out STD_LOGIC;
           MemtoReg : out STD_LOGIC;
           RegWrite : out STD_LOGIC;
           Br_ne : out STD_LOGIC
           );
end component;

component EX is
    Port ( rd1 : in STD_LOGIC_VECTOR (31 downto 0);
           aluSrc : in STD_LOGIC;
           rd2 : in STD_LOGIC_VECTOR (31 downto 0);
           ext_imm : in STD_LOGIC_VECTOR (31 downto 0);
           sa : in STD_LOGIC_VECTOR (4 downto 0);
           func : in STD_LOGIC_VECTOR (5 downto 0);
           aluOp : in STD_LOGIC_VECTOR (2 downto 0);
           pc : in STD_LOGIC_VECTOR (31 downto 0);
           rt: in STD_LOGIC_VECTOR(4 downto 0);
           rd: in STD_LOGIC_VECTOR(4 downto 0);
           RegDst: in STD_LOGIC;
           zero : out STD_LOGIC;
           aluRes : out STD_LOGIC_VECTOR (31 downto 0);
           branchAdress : out STD_LOGIC_VECTOR (31 downto 0);
           rWA: out STD_LOGIC_VECTOR(4 downto 0)
           );
end component;

component mem is
    Port ( memWrite : in STD_LOGIC;
           aluResIn : in STD_LOGIC_VECTOR (31 downto 0);
           rd2 : in STD_LOGIC_VECTOR (31 downto 0);
           clk : in STD_LOGIC;
           en : in STD_LOGIC;
           memData : out STD_LOGIC_VECTOR (31 downto 0);
           aluResOut : out STD_LOGIC_VECTOR (31 downto 0));
end component;

begin

monopulse: MPG port map(clk=>clk, btn=>btn(0),enable=>en);
display: SSD port map(clk=>clk, digits=>output, an=>an, cat=>cat);

ifect: IFetch port map(clk=>clk, Jump => JumpSeg, JumpAddress => jumpAdressFinal, PCSrc => PCSrcFinal, BranchAddress => BrAdress_EX_MEM, en=>en, rst=>btn(1),Instruction => InstructionSeg , PC => PCSeg);
uc1: UC port map(Instr => Instruction_IF_ID(31 downto 26), RedDst => RedDstSeg , ExtOp => ExtOpSeg, ALUSrc => AluSrcSeg, Branch => BranchSeg, Jump => JumpSeg, ALUOp => AluOpSeg, MemWrite => MemWriteSeg, MemtoReg => MemtoRegSeg, RegWrite => RegWriteSeg, Br_ne => Br_neSeg);
id1: ID port map(clk => clk, RegWrite => RegWrite_MEM_WB , Instr => Instruction_IF_ID(25 downto 0), Wa => WA_MEM_WB, en => en, ExtOp => ExtOpSeg, rd1 => rd1Seg, rd2 => rd2Seg, wd => finalMuxSeg, ext_imm => ext_immSeg, funt => funtSeg, sa => saSeg, rt => rtSeg, rd => rdSeg);
ex1: EX port map(rd1 => RD1_ID_EX, aluSrc => AluSrc_ID_EX, rd2 => RD2_ID_EX, ext_imm => Ext_Imm_ID_EX, sa => sa_ID_EX, func => funct_ID_EX, aluOp => AluOp_ID_EX, pc => Pcp4_ID_EX, rt => Rt_ID_EX, rd => Rd_ID_EX, RegDst => RegDst_ID_EX, zero => zeroSeg , aluRes => aluResSeg, branchAdress => branchAdressSeg, rWA => rWASeg);
mem1: mem port map(memWrite => MemWrite_EX_MEM, aluResIn => ALURes_EX_MEM, rd2 => RD2_EX_MEM, clk => clk, en => en, memData => memDataSeg, aluResOut => aluResOutSeg);

jumpAdress1 <= Instruction_IF_ID(25 downto 0) & "00";
jumpAdress2 <= PCp4_IF_ID(31 downto 28);
jumpAdressFinal <= jumpAdress2 & jumpAdress1;

Branch1 <= Branch_EX_MEM and Zero_EX_MEM;
Branch2 <=  not(Zero_EX_MEM) and BNE_EX_MEM;
PCSrcFinal <= Branch1 or Branch2;

finalMuxSeg <= ALURes_MEM_WB when Mem2Reg_MEM_WB = '0' else MemData_MEM_WB;

process(clk)
begin
    if rising_edge(clk) then
        if en = '1' then
            --IF/ID
            Instruction_IF_ID <= InstructionSeg;
            PCp4_IF_ID <= PCSeg;
            
            --ID/EX
            RegDst_ID_EX <= RedDstSeg;
            AluSrc_ID_EX <= AluSrcSeg;
            Branch_ID_EX <= BranchSeg;
            AluOp_ID_EX <= AluOpSeg;
            MemWrite_ID_EX <= MemWriteSeg;
            Mem2Reg_ID_EX <= MemtoRegSeg;
            RegWrite_ID_EX <= RegWriteSeg;
            RD1_ID_EX <= rd1Seg;
            BNE_ID_EX <= Br_neSeg;
            RD2_ID_EX <= rd2Seg;
            Ext_Imm_ID_EX <= ext_immSeg;
            funct_ID_EX <= funtSeg;
            sa_ID_EX <= saSeg;
            Rd_ID_EX <= rdSeg;
            Rt_ID_EX <= rtSeg;
            Pcp4_ID_EX <= PCp4_IF_ID;
            
            --EX/MEM
            Branch_EX_MEM <= Branch_ID_EX;
            MemWrite_EX_MEM <= MemWrite_ID_EX;
            Mem2Reg_EX_MEM <= Mem2Reg_ID_EX;
            RegWrite_EX_MEM <= RegWrite_ID_EX;
            Zero_EX_MEM <= zeroSeg;
            BrAdress_EX_MEM <= branchAdressSeg;
            ALURes_EX_MEM <= aluResSeg;
            WA_EX_MEM <= rWASeg;
            BNE_EX_MEM <= BNE_ID_EX;
            RD2_EX_MEM <= RD2_ID_EX;
            
            --MEM/WB
            RegWrite_MEM_WB <= RegWrite_EX_MEM;
            Mem2Reg_MEM_WB <= Mem2Reg_EX_MEM;
            ALURes_MEM_WB <= aluResOutSeg;
            MemData_MEM_WB <= memDataSeg; 
            WA_MEM_WB <= WA_EX_MEM;
        end if;
    end if;    
end process;

process(sw(7 downto 5), InstructionSeg, PCSeg, rd1Seg, rd2Seg, ext_immSeg, aluResSeg, memDataSeg, finalMuxSeg)
begin
  case sw(7 downto 5) is 
    when "000" =>
      output <= InstructionSeg;
    when "001" =>
      output <= PCSeg;
    when "010" =>
      output <= RD1_ID_EX;
    when "011" =>
      output <= RD2_ID_EX;
    when "100" =>
      output <= Ext_Imm_ID_EX;
    when "101" =>
       output <= aluResSeg;
    when "110" =>
       output <= memDataSeg;
     when "111" =>        
       output <= finalMuxSeg; --WD
    when others =>
      output <= (others => '0');
  end case;
end process;

led(11) <= Br_neSeg;
led(7) <= RedDstSeg;
led(6) <= ExtOpSeg;
led(5) <= ALUSrcSeg;
led(4) <= BranchSeg;
led(3) <= JumpSeg;
led(2) <= MemWriteSeg;
led(1) <= MemtoRegSeg;
led(0) <= RegWriteSeg;

led(8) <= ALUOpSeg(0);
led(9) <= ALUOpSeg(1);
led(10) <= ALUOpSeg(2);


end Behavioral;
