--Nexys4
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity test_env is
    Port ( clk : in STD_LOGIC;
           btn : in STD_LOGIC_VECTOR (4 downto 0);
           sw : in STD_LOGIC_VECTOR (15 downto 0);
           led : out STD_LOGIC_VECTOR (15 downto 0);
           an : out STD_LOGIC_VECTOR (3 downto 0);
           cat : out STD_LOGIC_VECTOR (6 downto 0)
           );
end test_env;

architecture Behavioral of test_env is

signal funtSeg: std_logic_vector (5 downto 0);
signal saSeg: std_logic_vector (4 downto 0);
signal jumpAdress2: std_logic_vector (3 downto 0);
signal jumpAdress1: std_logic_vector (27 downto 0);
signal en: std_logic := '0';
signal output, InstructionSeg, PCSeg, rd1Seg, rd2Seg, finalMuxSeg, ext_immSeg, branchAdressSeg, aluResSeg, aluResOutSeg, memDataSeg, jumpAdressFinal : std_logic_vector(31 downto 0);
signal RedDstSeg, ExtOpSeg, ALUSrcSeg, BranchSeg, JumpSeg, MemWriteSeg, MemtoRegSeg, RegWriteSeg, zeroSeg, PCSrcFinal,Br_neSeg, Branch1, Branch2: std_logic;
signal ALUOpSeg: std_logic_vector(2 downto 0);

component MPG is
    Port ( 
    clk : in STD_LOGIC;
    btn: in STD_LOGIC;
    enable : out STD_LOGIC
    );
end component;


component SSD is
    Port ( clk : in STD_LOGIC;
           digits : in STD_LOGIC_VECTOR(15 downto 0);
           an : out STD_LOGIC_VECTOR(3 downto 0);
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
           RegDst : in STD_LOGIC;
           en : in STD_LOGIC;
           ExtOp : in STD_LOGIC;
           rd1 : out STD_LOGIC_VECTOR (31 downto 0);
           rd2 : out STD_LOGIC_VECTOR (31 downto 0);
           wd : in STD_LOGIC_VECTOR (31 downto 0);
           ext_imm : out STD_LOGIC_VECTOR (31 downto 0);
           funt : out STD_LOGIC_VECTOR (5 downto 0);
           sa : out STD_LOGIC_VECTOR (4 downto 0));
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
           zero : out STD_LOGIC;
           aluRes : out STD_LOGIC_VECTOR (31 downto 0);
           branchAdress : out STD_LOGIC_VECTOR (31 downto 0));
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
ifect: IFetch port map(clk=>clk, Jump => JumpSeg, JumpAddress => jumpAdressFinal, PCSrc => PCSrcFinal, BranchAddress=> branchAdressSeg, en=>en, rst=>btn(1),Instruction=>InstructionSeg, PC=>PCSeg);
display: SSD port map(clk=>clk, digits=>output, an=>an, cat=>cat);
uc1: UC port map(Instr => InstructionSeg(31 downto 26), RedDst => RedDstSeg, ExtOp => ExtOpSeg, ALUSrc => ALUSrcSeg, Branch => BranchSeg, Jump => JumpSeg, ALUOp => ALUOpSeg, MemWrite => MemWriteSeg, MemtoReg => MemtoRegSeg, RegWrite => RegWriteSeg, Br_ne => Br_neSeg);
id1: ID port map(clk => clk, RegWrite => RegWriteSeg, Instr => InstructionSeg(25 downto 0), RegDst =>  RedDstSeg, en => en, ExtOp => ExtOpSeg, rd1 => rd1Seg, rd2 => rd2Seg, wd => finalMuxSeg, ext_imm => ext_immSeg, funt => funtSeg, sa => saSeg);
ex1: EX port map(rd1 => rd1Seg, aluSrc => ALUSrcSeg, rd2 => rd2Seg, ext_imm => ext_immSeg, sa => saSeg, func => funtSeg, aluOp => ALUOpSeg, pc => PCSeg, zero => zeroSeg , aluRes => aluResSeg, branchAdress => branchAdressSeg);
mem1: mem port map(memWrite => MemWriteSeg, aluResIn => aluResSeg, rd2 => rd2Seg, clk => clk, en => en, memData => memDataSeg, aluResOut => aluResOutSeg );

jumpAdress1 <= InstructionSeg(25 downto 0) & "00";
jumpAdress2 <= PCSeg(31 downto 28);
jumpAdressFinal <= jumpAdress2 & jumpAdress1;

Branch1 <= BranchSeg and zeroSeg;
Branch2 <=  not(zeroSeg) and Br_neSeg;
PCSrcFinal <= Branch1 or Branch2;

finalMuxSeg <= aluResOutSeg when MemtoRegSeg = '0' else memDataSeg;

process(sw(7 downto 5), InstructionSeg, PCSeg, rd1Seg, rd2Seg, ext_immSeg, aluResSeg, memDataSeg, finalMuxSeg)
begin
  case sw(7 downto 5) is 
    when "000" =>
      output <= InstructionSeg;
    when "001" =>
      output <= PCSeg;
    when "010" =>
      output <= rd1Seg;
    when "011" =>
      output <= rd2Seg;
    when "100" =>
      output <= ext_immSeg;
    when "101" =>
       output <= aluResSeg;
    when "110" =>
       output <= memDataSeg;
     when "111" =>        
       output <= finalMuxSeg;
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
