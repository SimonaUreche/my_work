library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;

entity IFetch is
    Port ( clk : in STD_LOGIC;
           Jump : in STD_LOGIC;
           JumpAddress : in STD_LOGIC_VECTOR (31 downto 0);
           PCSrc : in STD_LOGIC;
           BranchAddress : in STD_LOGIC_VECTOR (31 downto 0);
           en : in STD_LOGIC;
           rst : in STD_LOGIC;
           Instruction : out STD_LOGIC_VECTOR (31 downto 0);
           PC : out STD_LOGIC_VECTOR (31 downto 0));
end IFetch;

architecture Behavioral of IFetch is

signal Q, data, outputMux1, outputMux2, outputSum: std_logic_vector(31 downto 0);

type rom_type is array (0 to 31) of std_logic_vector(31 downto 0);
signal rom : rom_type := (

b"000111_00000_00001_0000000000000000", -- X"1C01 0000". 00 - lw $1, 0($0) - incarcam adresa sirului din memorie in registrul 1 (adresa de inceput a sirului)
b"000111_00000_00010_0000000000000100", -- X"1C02 0004". 01 - lw $2, 4($0) - incarcam valoarea lui N in registrul 2 (numarul de elemente al sirului)

b"000001_00000_00011_0000000000000001", -- X"0403 0001". 02 - addi $3,$0, 1 - contor pentru a stii cand sa ne oprim in caz ca sirul este ordonat

----loop:
b"000111_00001_00100_0000000000000000", -- X"1C24 0000". 03 - lw $4, 0($1)  - incarcam valoarea curenta din memorie in registrul 4
b"000111_00001_00101_0000000000000100", -- X"1C25 0004". 04 - lw $5, 4($1) - incarcam valoarea urmatoare din memorie in registrul 5

b"000000_00100_00101_00110_00000_010000", --X"0085 3010". 05 - slt $6, $4, $5 - setam registrul $6 la 1 daca $4 < $5, altfel la 0
b"000100_00110_00000_0000000000000111", -- X"10C0 0007". 06 - beq $6, $0, 7(not_sorted) - daca $4 >= $5 => iesim din bucla(vector neordonat)

b"000001_00001_00001_0000000000000100", -- X"0421 0004". 07 - addi $1,$1, 4 - incrementam adresa pentru a trece la urmatorul element
b"000001_00011_00011_0000000000000001", -- X"0463 0001". 08 - addi $3,$3, 1 - incrementam contorul pentru a trece la urmatorul element

b"000110_00011_00010_1111111111111001", -- X"1862 FFF9". 09 - bne $3, $2, -7(loop) - daca contorul este mai mic decât N, continuam loop-ul
----end loop:

--sorted - ajungem aici doar daca sirul este ordonat crescator                                                                                             
b"000101_00000_01000_0000000000000001", -- X"1408 0001". 10 - ori $8,$0, 1 - sirul e sortat => 1 = true   
b"000011_00000_01000_0000000000001000", --  X"0C08 0008". 11 - sw $8, 8($0) - scriem rezultatul la adresa 8
b"000111_00000_01001_0000000000001000", --  X"1C09 0008". 12 - lw $9, 8($0) - verificam rezultatul
b"100000_00000000000000000000010111", --  X"8000 0017". 13 j 17

--not sorted
b"000101_00000_01000_0000000000000000", -- X"1408 0000",. 14 - ori $8,$0, 0 - sirul nu e sortat => 0 = false
b"000011_00000_01000_0000000000001000", -- X"0C08 0008". 15 - sw $8, 8($0) - scriem rezultatul la adresa 8
b"000111_00000_01001_0000000000001000", --  X"1C09 0008". 16 - lw $9, 8($0) - verificam rezultatul

others =>  X"00000000");

signal addr: std_logic_vector(4 downto 0);

begin

process(clk, rst)
    begin
    if rst = '1' then
        Q <= (others => '0');
        elsif rising_edge(clk) then
        if en = '1' then 
            Q <= outputMux2;
         end if;
      end if;
end process;

addr <= Q(6 downto 2);

Instruction <= rom(conv_integer(addr));

sum1: outputSum <= 4 + Q;

mux1:process(BranchAddress, outputSum, PCSrc)
        begin
        if PCSrc = '1' then
        outputMux1 <= BranchAddress;
        else
        outputMux1 <= outputSum;
        end if;
end process;

mux2:process(JumpAddress, outputMux1, Jump)
        begin
        if Jump = '1' then
        outputMux2 <= JumpAddress;
        else
        outputMux2 <= outputMux1;
        end if;
end process;

PC <= outputSum;

end Behavioral;
