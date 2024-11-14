library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.numeric_std.ALL;

entity EX is
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
end EX;

architecture Behavioral of EX is

signal aluCtrl: STD_LOGIC_VECTOR(2 downto 0);
signal A, B, C: std_logic_vector(31 downto 0);

begin

ALUControl: process(aluOp, func)
begin
    case aluOp is
        when "000" =>
            case func is
            when "000000" => aluCtrl <= "000";--(+)
            when "000001" => aluCtrl <= "001";--(-)
            when "000010" => aluCtrl <= "101";--(<<|)
            when "000011" => aluCtrl <= "110";--(>>|)
            when "100000" => aluCtrl <= "010";--(&)
            when "100001" => aluCtrl <= "011";--(|)
            when "100010" => aluCtrl <= "100";--(^)
            when "010000" => aluCtrl <= "111";--(<)
            when others => aluCtrl <= (others=>'X');
            end case;
        when "001" => aluCtrl <= "000";--(+)
        when "010" => aluCtrl <= "001";--(-)
        when "011" => aluCtrl <= "011";--(|)
        when others => aluCtrl <= (others=>'X');
     end case;
end process;

A <= rd1;
B <= rd2 when aluSrc = '0' else ext_imm;

process(A, B, aluCtrl,sa)
begin
    case aluCtrl is
        when "000" => C <= A + B;
        when "001" => C <= A - B;
        when "101" => C <= to_stdlogicvector(to_bitvector(B) sll conv_integer(sa));
        when "110" => C <= to_stdlogicvector(to_bitvector(B) srl conv_integer(sa));
        when "010" => C <= A and B;
        when "011" => C <= A or B;
        when "100" => C <= A xor B;
        when "111" => 
            if signed(A) < signed(B) then 
                C <= X"00000001"; 
            else 
                C <= X"00000000"; 
            end if;       
         when others => C <= (others=>'X');
    end case;
end process;

aluRes <= C;
zero <= '1' when C = 0 else '0';
branchAdress <= (ext_imm(29 downto 0)& "00") + pc;

rWA <= rt when RegDst = '0' else rd;

end Behavioral;
