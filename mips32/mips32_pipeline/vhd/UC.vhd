library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity UC is
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
end UC;

architecture Behavioral of UC is

begin

process(Instr)
begin

RedDst <= '0';
ExtOp <= '0';
ALUSrc <= '0';
Branch <= '0';
Br_ne <= '0';
Jump <= '0';
ALUOp <= "000";
MemWrite <= '0';
MemtoReg <= '0';
RegWrite <= '0';

case Instr is
    when "000000" => --Tip R
    RedDst <= '1'; RegWrite <= '1'; ALUOp <= "000";
    
   when "000001" => --ADDI
   ExtOp <= '1';ALUSrc <= '1'; RegWrite <= '1'; ALUOp <= "001";
   
   when "000111" => --LW
   ExtOp <= '1';ALUSrc <= '1'; MemtoReg <= '1'; RegWrite <= '1'; ALUOp <= "001";
   
   when "000011" => --SW
   ExtOp <= '1';ALUSrc <= '1'; MemWrite <= '1'; ALUOp <= "001";        
    
   when "000100" => --BEQ
   ExtOp <= '1'; Branch <= '1'; ALUOp <= "010";                      
   
   when "000101" => --ORI
   ALUSrc <= '1';RegWrite <= '1';  ALUOp <= "011";
   
   when "000110" => --BNE
    ExtOp <= '1'; Br_ne <= '1';  ALUOp <= "010"; 
   
   when "100000" => --JUMP
   Jump <= '1';
   
   when others => 
       RedDst <= 'X'; ExtOp <= 'X'; ALUSrc <= 'X'; 
       Branch <= 'X'; Jump <= 'X'; MemWrite <= 'X';
       MemtoReg <= 'X'; RegWrite <= 'X';
       ALUOp <= "XXX";
end case;
end process;

end Behavioral;