.386
.model flat, stdcall

;includem biblioteci și declarăm ce funcții vrem să importăm
includelib msvcrt.lib
extern exit: proc
extern malloc: proc
extern memset: proc
extern printf: proc
includelib canvas.lib
extern BeginDrawing: proc

;declarăm simbolul start ca public - de acolo începe execuția
public start

;secțiunile programului, date, respectiv cod
.data ;aici declarăm date

;declarăm zona de desenat
window_title DB "CHESS",0 ;titlul proiectului

;dimensiunile paginii de desenat
area_width EQU 640  
area_height EQU 640 
area DD 0

;dimensiunile imaginilor(piesele de sah)
desen_width EQU 48
desen_height EQU 48


counter DD 0

;pentru a face codul mai lizibil declaram niste constante; in acest mod o sa stim cand ne referim la primul argument, al doilea..etc
arg1 EQU 8
arg2 EQU 12
arg3 EQU 16
arg4 EQU 20

;variabile pe care le folosim la afisarea literelor
symbol_width EQU 10
symbol_height EQU 20

;de urmatoarele 3 variabile ne folosim pe parcursul codului la: desenarea patratelor, colorarea patratelor, afisarea imaginilor, etc.
buton_x EQU 30
buton_y EQU 60
linie_size EQU 70

;variabile pentru a putea muta pionii albi
;mut pion linia 7 coloana A o pozitie
button_pion_albx EQU 30 
button_pion_alby EQU 410
;mut pion linia 7 coloana B o pozitie
button_pion_alb1x EQU 100 
button_pion_alb1y EQU 410
;mut pion linia 7 coloana C 2 pozitii
button_pion_alb2x EQU 170 
button_pion_alb2y EQU 340 
;mut pion linia 7 coloana D o pozitie
button_pion_alb3x EQU 240 
button_pion_alb3y EQU 410
;mut pion linia 7 coloana E 2 pozitii
button_pion_alb4x EQU 310 
button_pion_alb4y EQU 340 
;mut pion linia 7 coloana F o pozitie
button_pion_alb5x EQU 380 
button_pion_alb5y EQU 410
;mut pion linia 7 coloana G 2 pozitii
button_pion_alb6x EQU 450 
button_pion_alb6y EQU 340
;mut pion linia 7 coloana H o pozitie
button_pion_alb7x EQU 520
button_pion_alb7y EQU 410

;variabile pentru a putea muta pionii negri
;mut pion linia 2 coloana A 2 pozitii
button_pion_negrux EQU 30 
button_pion_negruy EQU 270
;mut pion linia 2 coloana B o pozitie
button_pion_negru1x EQU 100 
button_pion_negru1y EQU 200
;mut pion linia 2 coloana C 2 pozitii
button_pion_negru2x EQU 170 
button_pion_negru2y EQU 270 
;mut pion linia 2 coloana D o pozitie
button_pion_negru3x EQU 240 
button_pion_negru3y EQU 200
;mut pion linia 2 coloana E 2 pozitii
button_pion_negru4x EQU 310 
button_pion_negru4y EQU 200
;mut pion linia 2 coloana F o pozitie
button_pion_negru5x EQU 380 
button_pion_negru5y EQU 270
;mut pion linia 2 coloana G 2 pozitii
button_pion_negru6x EQU 450
button_pion_negru6y EQU 200
;mut pion linia 2 coloana H o pozitie
button_pion_negru7x EQU 520
button_pion_negru7y EQU 270

;variabile pentru a putea muta caii
;mut caii negrii
button_cal_negru_2x EQU 380 
button_cal_negru_2y EQU 200 
button_cal_negru_1x EQU 170
button_cal_negru_1y EQU 200 

;mut caii albi
button_cal_alb_2x EQU 380 
button_cal_alb_2y EQU 200 
button_cal_alb_1x EQU 170 
button_cal_alb_1y EQU 400

;variabile pentru a putea muta turnurile
;mut turnurile albe
button_turn_alb_2x EQU 520 
button_turn_alb_2y EQU 480
button_turn_alb_1x EQU 30
button_turn_alb_1y EQU 480

;mut turnurile_negre
button_turn_negru_2x EQU 520
button_turn_negru_2y EQU 200
button_turn_negru_1x EQU 30
button_turn_negru_1y EQU 130

;variabile pentru a putea muta nebunii
;mut nebunii albi
button_nebun_alb_2x EQU 310
button_nebun_alb_2y EQU 480
button_nebun_alb_1x EQU 310
button_nebun_alb_1y EQU 410

;mut nebunii negri
button_nebun_negru_1x EQU 100
button_nebun_negru_1y EQU 130

;variabile pentru a putea muta reginele
button_regina_x EQU 30 
button_regina_y EQU 340 

button_regina_x1 EQU 300
button_regina_y1 EQU 130

;folosim variabilele pentru mentinerea permanenta a click-ului la mutarea unor piese
;variabile pe care le folosim la mutarea pionilor albi
salvare dd 0
salvare3 dd 0
salvare4 dd 0
salvare5 dd 0
salvare6 dd 0
salvare7 dd 0
salvare8 dd 0
salvare9 dd 0

;variabile pe care le folosim la mutarea pionilor negrii
salvare10 dd 0
salvare11 dd 0
salvare12 dd 0
salvare13 dd 0
salvare14 dd 0
salvare15 dd 0
salvare16 dd 0
salvare17 dd 0

;variabile pe care le folosim la mutarea cailor 
salvare1 dd 0
salvare18 dd 0
salvare19 dd 0

;variabile pe care le folosim la mutarea turnurilor
salvare20 dd 0
salvare21 dd 0
salvare22 dd 0
salvare23 dd 0

;variabile pe care le folosim la mutarea nebunilor
salvare24 dd 0
salvare25 dd 0
salvare26 dd 0

;variabile pe care le folosim la mutarea reginelor
salvare27 dd 0
salvare2 dd 0
salvare28 dd 0

include digits.inc
include letters.inc
include desen1.inc

.code

; procedura make_text afiseaza o litera sau o cifra la coordonatele date
; arg1 - simbolul de afisat (litera sau cifra)
; arg2 - pointer la vectorul de pixeli
; arg3 - pos_x
; arg4 - pos_y

make_text proc
	push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, 'A'
	jl make_digit
	cmp eax, 'Z'
	jg make_digit
	sub eax, 'A'
	lea esi, letters
	jmp draw_text
make_digit:
	cmp eax, '0'
	jl make_space
	cmp eax, '9'
	jg make_space
	sub eax, '0'
	lea esi, digits
	jmp draw_text
make_space:	
	mov eax, 26 ; de la 0 pana la 25 sunt litere, 26 e space
	lea esi, letters
	
draw_text:
	mov ebx, symbol_width
	mul ebx
	mov ebx, symbol_height
	mul ebx
	add esi, eax
	mov ecx, symbol_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, symbol_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, symbol_width
bucla_simbol_coloane:
	cmp byte ptr [esi], 0
	je simbol_pixel_alb
	mov dword ptr [edi], 0
	jmp simbol_pixel_next
simbol_pixel_alb:
	mov dword ptr [edi], 0FFFFFFh
simbol_pixel_next:
	inc esi
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
make_text endp

; un macro ca sa apelam mai usor desenarea simbolului
make_text_macro macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call make_text
	add esp, 16
endm

					;facem 12 proceduri, una pentru desenarea fiecarei piesa a tablei de sah
;procedura desenare rege negru
rege_negru proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, reg_n
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
rege_negru endp
; un macro ca sa apelam mai usor desenarea 
make_desen_rege_negru macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call rege_negru
	add esp, 16
endm

;procedura desenare rege alb
rege_alb proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, reg_a
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
rege_alb endp
; un macro ca sa apelam mai usor desenarea 
make_desen_rege_alb macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call rege_alb
	add esp, 16
endm

;procedura desenare regina alba
regina_alba proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, rgi_a
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
regina_alba endp
; un macro ca sa apelam mai usor desenarea 
make_desen_regina_alba macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call regina_alba
	add esp, 16
endm

;procedura desenare regina neagra
regina_neagra proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, rgi_n
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
regina_neagra endp
; un macro ca sa apelam mai usor desenarea 
make_desen_regina_neagra macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call regina_neagra
	add esp, 16
endm

;procedura desenare turn negru(fundal bej)
turn_negru proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, tur_n
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
turn_negru endp
; un macro ca sa apelam mai usor desenarea 
make_desen_turn_negru macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call turn_negru
	add esp, 16
endm

;procedura desenare turn negru2(fundal maro)
turn_negru2 proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, tur_n2
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
turn_negru2 endp
; un macro ca sa apelam mai usor desenarea 
make_desen_turn_negru2 macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call turn_negru2
	add esp, 16
endm

;procedura desenare turn alb(fundal bej)
turn_alb proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, tur_a
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
turn_alb endp
; un macro ca sa apelam mai usor desenarea 
make_desen_turn_alb macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call turn_alb
	add esp, 16
endm

;procedura desenare turn alb2(fundal maro)
turn_alb2 proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, tur_a2
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
turn_alb2 endp
; un macro ca sa apelam mai usor desenarea 
make_desen_turn_alb2 macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call turn_alb2
	add esp, 16
endm

;procedura desenare nebun alb(fundal bej)
nebun_alb proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, neb_a
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
nebun_alb endp
; un macro ca sa apelam mai usor desenarea 
make_desen_nebun_alb macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call nebun_alb
	add esp, 16
endm

;procedura desenare nebun alb2(fundal maro)
nebun_alb2 proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, neb_a2
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
nebun_alb2 endp
; un macro ca sa apelam mai usor desenarea 
make_desen_nebun_alb2 macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call nebun_alb2
	add esp, 16
endm

;procedura desenare nebun negru(fundal bej)
nebun_negru proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, neb_n
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
nebun_negru endp
; un macro ca sa apelam mai usor desenarea 
make_desen_nebun_negru macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call nebun_negru
	add esp, 16
endm

;procedura desenare nebun negru2(fundal maro)
nebun_negru2 proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, neb_n2
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
nebun_negru2 endp
; un macro ca sa apelam mai usor desenarea 
make_desen_nebun_negru2 macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call nebun_negru2
	add esp, 16
endm

;procedura desenare cal negru(fundal bej)
cal_negru proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, cal_n
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
cal_negru endp
; un macro ca sa apelam mai usor desenarea 
make_desen_cal_negru macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call cal_negru
	add esp, 16
endm

;procedura desenare cal negru2(fundal maro)
cal_negru2 proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, cal_n2
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
cal_negru2 endp
; un macro ca sa apelam mai usor desenarea 
make_desen_cal_negru2 macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call cal_negru2
	add esp, 16
endm

;procedura de desenare cal alb(fundal bej)
cal_alb proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, cal_a
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
cal_alb endp
; un macro ca sa apelam mai usor desenarea 
make_desen_cal_alb macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call cal_alb
	add esp, 16
endm

;procedura de desenare cal alb2(fundal maro)
cal_alb2 proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, cal_a2
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
cal_alb2 endp
; un macro ca sa apelam mai usor desenarea 
make_desen_cal_alb2 macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call cal_alb2
	add esp, 16
endm

;procedura desenare pion alb(fundal bej)
pion_alb proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, pio_a
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
pion_alb endp
; un macro ca sa apelam mai usor desenarea 
make_desen_pion_alb macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call pion_alb
	add esp, 16
endm

;procedura desenare pion alb2(fundal maro)
pion_alb2 proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, pio_a2
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
pion_alb2 endp
; un macro ca sa apelam mai usor desenarea 
make_desen_pion_alb2 macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call pion_alb2
	add esp, 16
endm

;procedura desenare pion negru(fundal bej)
pion_negru proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, pio_n
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
pion_negru endp
; un macro ca sa apelam mai usor desenarea 
make_desen_pion_negru macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call pion_negru
	add esp, 16
endm

;procedura desenare pion negru(fundal maro)
pion_negru2 proc
push ebp
	mov ebp, esp
	pusha
	
	mov eax, [ebp+arg1] ; citim simbolul de afisat
	cmp eax, '^'
	je make_face
make_face:
	mov eax, 0
	lea esi, pio_n2
	jmp draw_text
draw_text:
	mov ebx, desen_width
	mul ebx
	mov ebx, desen_height
	mul ebx
	add esi, eax
	mov ecx, desen_height
bucla_simbol_linii:
	mov edi, [ebp+arg2] ; pointer la matricea de pixeli
	mov eax, [ebp+arg4] ; pointer la coord y
	add eax, desen_height
	sub eax, ecx
	mov ebx, area_width
	mul ebx
	add eax, [ebp+arg3] ; pointer la coord x
	shl eax, 2 ; inmultim cu 4, avem un DWORD per pixel
	add edi, eax
	push ecx
	mov ecx, desen_width
bucla_simbol_coloane:
mov ebx, [esi]
	mov ebx, [esi]
	mov [edi], ebx
	add esi, 4
	add edi, 4
	loop bucla_simbol_coloane
	pop ecx
	loop bucla_simbol_linii
	popa
	mov esp, ebp
	pop ebp
	ret
pion_negru2 endp
; un macro ca sa apelam mai usor desenarea 
make_desen_pion_negru2 macro symbol, drawArea, x, y
	push y
	push x
	push drawArea
	push symbol
	call pion_negru2
	add esp, 16
endm

;facem o linie orizotala pentru a putea face mai usor un patrat
linie_orizontala macro x, y, len, color ;x si y sunt pixelii, len se refera la lungimea liniei, iar color la culoare
 ;calculam pozitia unde se afla x si y in vectorul area ca sa putem incepe desenarea liniei de acolo
local bucla_linie
	mov eax, y ;EAX = y
	mov ebx, area_width
	mul ebx ; Eax = y * area_width
	add eax, x ;EAX = y * area_width + x
	shl eax, 2 ;EAX = (y * area_width + x) * 4 --in eax avem pozitia in vectorul de pixeli
	add eax, area
	mov ecx, len
bucla_linie:
	mov dword ptr[eax], color
	add eax, 4 ;ne deplasam la dreapta
	loop bucla_linie
endm

;facem o linie verticala pentru a putea face mai usor un patrat
linie_verticala macro x, y, len, color ;x si y sunt pixelii, len se refera la lungimea liniei, iar color la culoare
 ;calculam pozitia unde se afla x si y in vectorul area ca sa putem incepe desenarea liniei de acolo
local bucla_linie
	mov eax, y ;EAX = y
	mov ebx, area_width
	mul ebx ; Eax = y * area_width
	add eax, x ;EAX = y * area_width + x
	shl eax, 2 ;EAX = (y * area_width + x) * 4 --in eax avem pozitia in vectorul de pixeli
	add eax, area
	mov ecx, len
bucla_linie:
	mov dword ptr[eax], color
	add eax, area_width * 4 ;ne deplasam la dreapta
	loop bucla_linie
endm

;facem un patrat folosindu-ne de liniile verticale si orizontale facute alterior
patrat macro x, y, dimensiune, color
    linie_orizontala x, y, dimensiune, color ; linie de sus
    linie_orizontala x, y + dimensiune - 1, dimensiune, color ; linie de jos
    linie_verticala x, y, dimensiune, color ; linie din stânga
    linie_verticala x + dimensiune - 1, y, dimensiune, color ; linie din dreapta
endm

;facem un macro pentru a putea colora o anumita zona
coloare_zona macro x, y, dimensiune, culoare ; x-reprezinta parametrul de unde incepe zona de desenat, y-paramentrul unde se termina
   local bucla_linie, calcul_pozitie ;variabile locale de care ne folosim in interiorul macro-ului
    
    ; Calculăm poziția de start a zonei în vectorul de pixeli
    mov eax, y
    mov ebx, area_width
    mul ebx
    add eax, x
    shl eax, 2
    add eax, area
    
    ; Setăm culoarea pentru fiecare linie a zonei
    mov ecx, dimensiune
bucla_linie:
    push ecx ; Salvăm contorul buclei pentru a-l folosi în interiorul buclei de calculare a pozițiilor
    
    ; Calculăm poziția de start a liniei în vectorul de pixeli
    mov edi, eax
    
    ; Setăm culoarea pentru fiecare pixel din linie
    mov ecx, dimensiune
calcul_pozitie:
    mov dword ptr [edi], culoare
    add edi, 4 ; Ne deplasăm la următorul pixel în cadrul liniei
    loop calcul_pozitie
    
    pop ecx ; Restaurăm contorul buclei
    add eax, area_width * 4 ; Ne deplasăm la următoarea linie în cadrul zonei
    loop bucla_linie
endm

; functia de desenare - se apeleaza la fiecare click
; sau la fiecare interval de 200ms in care nu s-a dat click
; arg1 - evt (0 - initializare, 1 - click, 2 - s-a scurs intervalul fara click, 3 - s-a apasat o tasta)
; arg2 - x (in cazul apasarii unei taste, x contine codul ascii al tastei care a fost apasata)
; arg3 - y

draw proc
	push ebp
	mov ebp, esp
	pusha

	;pentru fiecare pion in parte avem declarata o variabina numita salvare_n de care ne folosim pentru mentinerea mai lunga a unui click
	;in momentul in care mutam o piesa, aceasta sa ramana permanent in locul mutat, nu doar la un semnal de click
	cmp salvare, 1
	je evt_click
	
	cmp salvare1, 1
	je evt_click
	
	cmp salvare2, 1
	je evt_click
	
	cmp salvare3, 1
	je evt_click
	
	cmp salvare4, 1
	je evt_click
	
	cmp salvare5, 1
	je evt_click
	
	cmp salvare6, 1
	je evt_click
	
	cmp salvare7, 1
	je evt_click
	
	cmp salvare8, 1
	je evt_click
	
	cmp salvare9, 1
	je evt_click
	
	cmp salvare10, 1
	je evt_click
	
	cmp salvare11, 1
	je evt_click
	
	cmp salvare12, 1
	je evt_click
	
	cmp salvare13, 1
	je evt_click
	
	cmp salvare9, 1
	je evt_click
	
	cmp salvare14, 1
	je evt_click
	
	cmp salvare15, 1
	je evt_click
	
	cmp salvare16, 1
	je evt_click
	
	cmp salvare17, 1
	je evt_click
	
	cmp salvare18, 1
	je evt_click
	
	cmp salvare19, 1
	je evt_click
	
	cmp salvare20, 1
	je evt_click
	
	cmp salvare21, 1
	je evt_click

	cmp salvare22, 1
	je evt_click
	
	cmp salvare23, 1
	je evt_click
	
	cmp salvare24, 1
	je evt_click
	
	cmp salvare25, 1
	je evt_click
	
	cmp salvare26, 1
	je evt_click
	
	cmp salvare27, 1
	je evt_click
	
	mov eax, [ebp+arg1]
	cmp eax, 1
	jz evt_click
	cmp eax, 2
	jz evt_timer  ;nu s-a efectuat click pe nimic
	
	;mai jos e codul care intializeaza fereastra cu pixeli albi
	mov eax, area_width
	mov ebx, area_height
	mul ebx
	shl eax, 2
	push eax
	push 255
	push area
	call memset
	add esp, 12
	jmp afisare_litere
	
evt_click:
		;incepem mutarea pionilor
		;toate variabilele de salvare declarate de inceputul codului au valoarea 0, deci urmatoarele 2 instructiuni se vor executa de fiecare data
		;executam practic un click continuu 
	cmp salvare, 1
	je salvare_pozitie

	;verificam daca coordonatele unde vrem sa mutam pionul alb se afla in interiorul "butonului" pe care o sa il apasam 
	mov eax, [ebp+arg2]
	cmp eax, button_pion_alb2x
	jl button_fail
	
	cmp eax, button_pion_alb2x + linie_size
	jg button_fail
	mov eax, [ebp+arg3]
	cmp eax, button_pion_alb2y
	jl button_fail
	cmp eax, button_pion_alb2y + linie_size
	jg button_fail
	
	;s-a dat click in interiorul butonului
	
salvare_pozitie:
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
	mov eax, 1
	mov salvare, eax
	
	make_desen_pion_alb '^', area, button_pion_alb2x +10 , button_pion_alb2y + 10
	coloare_zona buton_x + linie_size *2, buton_y+ linie_size*6 , linie_size, 0ffcba4h
	jmp button_fail
	
button_fail:	
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam calul se afla in interiorul "butonului" pe care o sa il apasam 
	cmp salvare1, 1
	je salvare_pozitie1
	
	mov eax, [ebp+arg2]
	cmp eax, button_cal_negru_2x
	jl button_fail1
	
	cmp eax, button_cal_negru_2x + linie_size
	jg button_fail1
	mov eax, [ebp+arg3]
	cmp eax, button_cal_negru_2y
	jl button_fail1
	cmp eax, button_cal_negru_2y + linie_size
	jg button_fail1
	;s-a dat click in interiorul butonului
	
salvare_pozitie1:
;in cazul in care coordonatele au fost verificate, mutam calul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
	mov eax, 1
	mov salvare1, eax
	
	make_desen_cal_negru '^', area, button_cal_negru_2x +10 , button_cal_negru_2y + 10
	coloare_zona buton_x + linie_size *6, buton_y , linie_size, 0ffcba4h
	jmp button_fail1
	
button_fail1:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam calul regina se afla in interiorul "butonului" pe care o sa il apasam 
	cmp salvare2, 1
	je salvare_pozitie2
	
	mov eax, [ebp+arg2]
	cmp eax, button_regina_x
	jl button_fail2
	
	cmp eax, button_regina_x + linie_size
	jg button_fail2
	mov eax, [ebp+arg3]
	cmp eax, button_regina_y
	jl button_fail2
	cmp eax, button_regina_y + linie_size
	jg button_fail2
	;s-a dat click in interiorul butonului
	
	;verificam daca nu cumva exista un alt pion in fata reginei
	cmp salvare, 1
	je salvare_pozitie2
	jmp button_fail2
	
salvare_pozitie2:
;in cazul in care coordonatele au fost verificate, mutam calul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)

	mov eax, 1
	mov salvare2, eax
	
	make_desen_regina_alba '^', area, button_regina_x +10 , button_regina_y + 10
	coloare_zona buton_x + linie_size *3, buton_y+ linie_size*7 , linie_size, 0ffcba4h
	jmp button_fail2

;incepem mutarile tuturor pionilor albi
button_fail2:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam pionul se afla in interiorul "butonului" pe care o sa il apasam 
	cmp salvare3, 1
	je salvare_pozitie3
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_albx
	jl button_fail3
	
	cmp eax, button_pion_albx + linie_size
	jg button_fail3
	mov eax, [ebp+arg3]
	cmp eax, button_pion_alby
	jl button_fail3
	cmp eax, button_pion_alby + linie_size
	jg button_fail3
	;s-a dat click in interiorul butonului
	
	
salvare_pozitie3:
	mov eax, 1
	mov salvare3, eax
	
	make_desen_pion_alb2 '^', area, button_pion_albx +10 , button_pion_alby + 10
	coloare_zona buton_x, buton_y+ linie_size*6 , linie_size, 0ffcba4h
	jmp button_fail3
	
button_fail3:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare4, 1
	je salvare_pozitie4
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_alb1x
	jl button_fail4
	
	cmp eax, button_pion_alb1x + linie_size
	jg button_fail4
	mov eax, [ebp+arg3]
	cmp eax, button_pion_alb1y
	jl button_fail4
	cmp eax, button_pion_alb1y + linie_size
	jg button_fail4
	;s-a dat click in interiorul butonului
	
salvare_pozitie4:
	mov eax, 1
	mov salvare4, eax
	
	make_desen_pion_alb '^', area, button_pion_alb1x +10 , button_pion_alb1y + 10
	coloare_zona buton_x + linie_size, buton_y+ linie_size*6 , linie_size, 0d48c44h
	jmp button_fail4
	
button_fail4:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
	cmp salvare5, 1
	je salvare_pozitie5
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_alb3x
	jl button_fail5
	
	cmp eax, button_pion_alb3x + linie_size
	jg button_fail5
	mov eax, [ebp+arg3]
	cmp eax, button_pion_alb3y
	jl button_fail5
	cmp eax, button_pion_alb3y + linie_size
	jg button_fail5
	;s-a dat click in interiorul butonului
	
salvare_pozitie5:
	mov eax, 1
	mov salvare5, eax
	
	make_desen_pion_alb '^', area, button_pion_alb3x +10 , button_pion_alb3y + 10
	coloare_zona buton_x + linie_size*3, buton_y+ linie_size*6 , linie_size, 0d48c44h
	jmp button_fail5

button_fail5:
	;se trece la "click-ul urmator"
	;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim) 
cmp salvare6, 1
	je salvare_pozitie6
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_alb4x
	jl button_fail6
	
	cmp eax, button_pion_alb4x + linie_size
	jg button_fail6
	mov eax, [ebp+arg3]
	cmp eax, button_pion_alb4y
	jl button_fail6
	cmp eax, button_pion_alb4y + linie_size
	jg button_fail6
	;s-a dat click in interiorul butonului
	
salvare_pozitie6:
	mov eax, 1
	mov salvare6, eax
	
	make_desen_pion_alb '^', area, button_pion_alb4x +10 , button_pion_alb4y + 10
	coloare_zona buton_x + linie_size*4, buton_y+ linie_size*6 , linie_size, 0ffcba4h
	jmp button_fail6
	
button_fail6:
	;se trece la "click-ul urmator"
	;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare7, 1
	je salvare_pozitie7
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_alb5x
	jl button_fail7
	
	cmp eax, button_pion_alb5x + linie_size
	jg button_fail7
	mov eax, [ebp+arg3]
	cmp eax, button_pion_alb5y
	jl button_fail7
	cmp eax, button_pion_alb5y + linie_size
	jg button_fail7
	;s-a dat click in interiorul butonului
	
salvare_pozitie7:
	mov eax, 1
	mov salvare7, eax
	
	make_desen_pion_alb '^', area, button_pion_alb5x +10 , button_pion_alb5y + 10
	coloare_zona buton_x + linie_size*5, buton_y+ linie_size*6 , linie_size, 0d48c44h
	jmp button_fail7
	
button_fail7:
	;se trece la "click-ul urmator"
	;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare8, 1
	je salvare_pozitie8
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_alb6x
	jl button_fail8
	
	cmp eax, button_pion_alb6x + linie_size
	jg button_fail8
	mov eax, [ebp+arg3]
	cmp eax, button_pion_alb6y
	jl button_fail8
	cmp eax, button_pion_alb6y + linie_size
	jg button_fail8
	;s-a dat click in interiorul butonului
	
salvare_pozitie8:
	mov eax, 1
	mov salvare8, eax
	
	make_desen_pion_alb '^', area, button_pion_alb6x +10 , button_pion_alb6y + 10
	coloare_zona buton_x + linie_size*6, buton_y+ linie_size*6 , linie_size, 0ffcba4h
	jmp button_fail8
	
	
button_fail8:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare9, 1
	je salvare_pozitie9
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_alb7x
	jl button_fail9
	
	cmp eax, button_pion_alb7x + linie_size
	jg button_fail9
	mov eax, [ebp+arg3]
	cmp eax, button_pion_alb7y
	jl button_fail9
	cmp eax, button_pion_alb7y + linie_size
	jg button_fail9
	;s-a dat click in interiorul butonului
	
salvare_pozitie9:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
	mov eax, 1
	mov salvare9, eax
	
	make_desen_pion_alb '^', area, button_pion_alb7x +10 , button_pion_alb7y + 10
	coloare_zona buton_x + linie_size*7, buton_y+ linie_size*6 , linie_size, 0d48c44h
	jmp button_fail9
;am mutat toti pionii albi

;incepem mutarile tuturor pionilor negrii
button_fail9:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare10, 1
	je salvare_pozitie10
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_negrux
	jl button_fail10
	
	cmp eax, button_pion_negrux + linie_size
	jg button_fail10
	mov eax, [ebp+arg3]
	cmp eax, button_pion_negruy
	jl button_fail10
	cmp eax, button_pion_negruy + linie_size
	jg button_fail10
	;s-a dat click in interiorul butonului
	
salvare_pozitie10:
	mov eax, 1
	mov salvare10, eax
	
	make_desen_pion_negru2 '^', area, button_pion_negrux +10 , button_pion_negruy + 10
	coloare_zona buton_x, buton_y+ linie_size , linie_size, 0d48c44h
	jmp button_fail10

button_fail10:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare11, 1
	je salvare_pozitie11
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_negru1x
	jl button_fail11
	
	cmp eax, button_pion_negru1x + linie_size
	jg button_fail11
	mov eax, [ebp+arg3]
	cmp eax, button_pion_negru1y
	jl button_fail11
	cmp eax, button_pion_negru1y + linie_size
	jg button_fail11
	;s-a dat click in interiorul butonului
	
salvare_pozitie11:
	mov eax, 1
	mov salvare11, eax
	
	make_desen_pion_negru2 '^', area, button_pion_negru1x +10 , button_pion_negru1y + 10
	coloare_zona buton_x + linie_size, buton_y+ linie_size , linie_size, 0ffcba4h
	jmp button_fail11

button_fail11:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare12, 1
	je salvare_pozitie12
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_negru2x
	jl button_fail12
	
	cmp eax, button_pion_negru2x + linie_size
	jg button_fail12
	mov eax, [ebp+arg3]
	cmp eax, button_pion_negru2y
	jl button_fail12
	cmp eax, button_pion_negru2y + linie_size
	jg button_fail12
	;s-a dat click in interiorul butonului
	
salvare_pozitie12:
	mov eax, 1
	mov salvare12, eax
	
	make_desen_pion_negru2 '^', area, button_pion_negru2x +10 , button_pion_negru2y + 10
	coloare_zona buton_x + linie_size*2, buton_y+ linie_size , linie_size, 0d48c44h
	jmp button_fail12

button_fail12:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare13, 1
	je salvare_pozitie13
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_negru3x
	jl button_fail13
	
	cmp eax, button_pion_negru3x + linie_size
	jg button_fail13
	mov eax, [ebp+arg3]
	cmp eax, button_pion_negru3y
	jl button_fail13
	cmp eax, button_pion_negru3y + linie_size
	jg button_fail13
	;s-a dat click in interiorul butonului
	
salvare_pozitie13:
	mov eax, 1
	mov salvare13, eax
	
	make_desen_pion_negru2 '^', area, button_pion_negru3x +10 , button_pion_negru3y + 10
	coloare_zona buton_x + linie_size*3, buton_y+ linie_size , linie_size, 0ffcba4h
	jmp button_fail13
	
button_fail13:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare14, 1
	je salvare_pozitie14
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_negru4x
	jl button_fail14
	
	cmp eax, button_pion_negru4x + linie_size
	jg button_fail14
	mov eax, [ebp+arg3]
	cmp eax, button_pion_negru4y
	jl button_fail14
	cmp eax, button_pion_negru4y + linie_size
	jg button_fail14
	;s-a dat click in interiorul butonului
	
salvare_pozitie14:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
	mov eax, 1
	mov salvare14, eax
	
	make_desen_pion_negru '^', area, button_pion_negru4x +10 , button_pion_negru4y + 10
	coloare_zona buton_x + linie_size*4, buton_y+ linie_size , linie_size, 0d48c44h
	jmp button_fail14
	
button_fail14:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare15, 1
	je salvare_pozitie15
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_negru5x
	jl button_fail15
	
	cmp eax, button_pion_negru5x + linie_size
	jg button_fail15
	mov eax, [ebp+arg3]
	cmp eax, button_pion_negru5y
	jl button_fail15
	cmp eax, button_pion_negru5y + linie_size
	jg button_fail15
	;s-a dat click in interiorul butonului
	
salvare_pozitie15:
	mov eax, 1
	mov salvare15, eax
	
	make_desen_pion_negru '^', area, button_pion_negru5x +10 , button_pion_negru5y + 10
	coloare_zona buton_x + linie_size*5, buton_y+ linie_size , linie_size, 0ffcba4h
	jmp button_fail15
	
button_fail15:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare16, 1
	je salvare_pozitie16
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_negru6x
	jl button_fail16
	
	cmp eax, button_pion_negru6x + linie_size
	jg button_fail16
	mov eax, [ebp+arg3]
	cmp eax, button_pion_negru6y
	jl button_fail16
	cmp eax, button_pion_negru6y + linie_size
	jg button_fail16
	;s-a dat click in interiorul butonului
	
salvare_pozitie16:
	mov eax, 1
	mov salvare16, eax
	
	make_desen_pion_negru '^', area, button_pion_negru6x +10 , button_pion_negru6y + 10
	coloare_zona buton_x + linie_size*6, buton_y+ linie_size , linie_size, 0d48c44h
	jmp button_fail16

button_fail16:
	;se trece la "click-ul urmator"
;in cazul in care coordonatele au fost verificate, mutam pionul(desenam unul nou la acea pozitie), iar pe cel vechi il acoperim)
cmp salvare17, 1
	je salvare_pozitie17
	
	mov eax, [ebp+arg2]
	cmp eax, button_pion_negru7x
	jl button_fail17
	
	cmp eax, button_pion_negru7x + linie_size
	jg button_fail17
	mov eax, [ebp+arg3]
	cmp eax, button_pion_negru7y
	jl button_fail17
	cmp eax, button_pion_negru7y + linie_size
	jg button_fail17
	;s-a dat click in interiorul butonului
	
salvare_pozitie17:
	mov eax, 1
	mov salvare17, eax
	
	make_desen_pion_negru '^', area, button_pion_negru7x +10 , button_pion_negru7y + 10
	coloare_zona buton_x + linie_size*7, buton_y+ linie_size , linie_size, 0ffcba4h
	jmp button_fail17

;am mutat toti pionii negrii

;mutam caii 
button_fail17:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam calul se afla in interiorul "butonului" pe care o sa il apasam 
cmp salvare18, 1
	je salvare_pozitie18
	
	mov eax, [ebp+arg2]
	cmp eax, button_cal_negru_1x
	jl button_fail18
	
	cmp eax, button_cal_negru_1x + linie_size
	jg button_fail18
	mov eax, [ebp+arg3]
	cmp eax, button_cal_negru_1y
	jl button_fail18
	cmp eax, button_cal_negru_1y + linie_size
	jg button_fail18
	;s-a dat click in interiorul butonului
	
salvare_pozitie18:
	mov eax, 1
	mov salvare18, eax
	
	make_desen_cal_negru2 '^', area, button_cal_negru_1x +10 , button_cal_negru_1y + 10
	coloare_zona buton_x + linie_size, buton_y , linie_size, 0d48c44h
	jmp button_fail18

button_fail18:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam calul se afla in interiorul "butonului" pe care o sa il apasam 
cmp salvare19, 1
	je salvare_pozitie19
	
	mov eax, [ebp+arg2]
	cmp eax, button_cal_alb_1x
	jl button_fail19
	
	cmp eax, button_cal_alb_1x + linie_size
	jg button_fail19
	mov eax, [ebp+arg3]
	cmp eax, button_cal_alb_1y
	jl button_fail19
	cmp eax, button_cal_alb_1y + linie_size
	jg button_fail19
	;s-a dat click in interiorul butonului
	
salvare_pozitie19:
	mov eax, 1
	mov salvare19, eax
	
	make_desen_cal_alb2 '^', area, button_cal_alb_1x +10 , button_cal_alb_1y + 10
	coloare_zona buton_x + linie_size, buton_y + linie_size*7, linie_size, 0ffcba4h
	jmp button_fail19
	
;am terminat de mutat caii

;mutam turnurile
button_fail19:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam turnul se afla in interiorul "butonului" pe care o sa il apasam 
cmp salvare20, 1
	je salvare_pozitie20
	
	mov eax, [ebp+arg2]
	cmp eax, button_turn_negru_1x
	jl button_fail20
	
	cmp eax, button_turn_negru_1x + linie_size
	jg button_fail20
	mov eax, [ebp+arg3]
	cmp eax, button_turn_negru_1y
	jl button_fail20
	cmp eax, button_turn_negru_1y + linie_size
	jg button_fail20
	;s-a dat click in interiorul butonului
	
	;verificam daca nu exista pion in fata primului turn negru
	cmp salvare10, 1
	je salvare_pozitie20
	jmp button_fail20
	
salvare_pozitie20:
	mov eax, 1
	mov salvare20, eax
	
	make_desen_turn_negru2 '^', area, button_turn_negru_1x +10 , button_turn_negru_1y + 10
	coloare_zona buton_x , buton_y, linie_size, 0ffcba4h
	jmp button_fail20
	
button_fail20:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam turnul se afla in interiorul "butonului" pe care o sa il apasam 
cmp salvare21, 1
	je salvare_pozitie21
	
	mov eax, [ebp+arg2]
	cmp eax, button_turn_negru_2x
	jl button_fail21
	
	cmp eax, button_turn_negru_2x + linie_size
	jg button_fail21
	mov eax, [ebp+arg3]
	cmp eax, button_turn_negru_2y
	jl button_fail21
	cmp eax, button_turn_negru_2y + linie_size
	jg button_fail21
	;s-a dat click in interiorul butonului
	
;verificam daca nu exista pion in fata celui de al doilea turn negru
	cmp salvare17, 1
	je salvare_pozitie21
	jmp button_fail21
	
salvare_pozitie21:
	mov eax, 1
	mov salvare21, eax
	
	make_desen_turn_negru2 '^', area, button_turn_negru_2x +10 , button_turn_negru_2y + 10
	coloare_zona buton_x + linie_size * 7, buton_y, linie_size, 0d48c44h
	jmp button_fail21

button_fail21:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam turnul se afla in interiorul "butonului" pe care o sa il apasam 
cmp salvare22, 1
	je salvare_pozitie22
	
	mov eax, [ebp+arg2]
	cmp eax, button_turn_alb_2x
	jl button_fail22
	
	cmp eax, button_turn_alb_2x + linie_size
	jg button_fail22
	mov eax, [ebp+arg3]
	cmp eax, button_turn_alb_2y
	jl button_fail22
	cmp eax, button_turn_alb_2y + linie_size
	jg button_fail22
	;s-a dat click in interiorul butonului
	
	;verificam daca nu exista pion in fata primului turn alb
	cmp salvare3, 1
	je salvare_pozitie22
	jmp button_fail22
	
salvare_pozitie22:
	mov eax, 1
	mov salvare22, eax
	
	make_desen_turn_alb2 '^', area, button_turn_alb_2x +10 , button_turn_alb_2y + 10
	coloare_zona buton_x + linie_size * 7, buton_y + linie_size * 7, linie_size, 0ffcba4h
	jmp button_fail22
button_fail22:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam turnul se afla in interiorul "butonului" pe care o sa il apasam 
cmp salvare23, 1
	je salvare_pozitie23
	
	mov eax, [ebp+arg2]
	cmp eax, button_turn_alb_1x
	jl button_fail23
	
	cmp eax, button_turn_alb_1x + linie_size
	jg button_fail23
	mov eax, [ebp+arg3]
	cmp eax, button_turn_alb_1y
	jl button_fail23
	cmp eax, button_turn_alb_1y + linie_size
	jg button_fail23
	;s-a dat click in interiorul butonului
	
	;verificam daca nu exista pion in fata celui de al doilea turn alb
	cmp salvare9, 1
	je salvare_pozitie23
	jmp button_fail23
	
salvare_pozitie23:
	mov eax, 1
	mov salvare23, eax
	
	make_desen_turn_alb '^', area, button_turn_alb_1x +10 , button_turn_alb_1y + 10
	coloare_zona buton_x , buton_y + linie_size * 7, linie_size, 0d48c44h
	jmp button_fail23
;am terminat de mutat turnurile

;mutam nebunii
button_fail23:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam nebunul se afla in interiorul "butonului" pe care o sa il apasam 
cmp salvare24, 1
	je salvare_pozitie24
	
	mov eax, [ebp+arg2]
	cmp eax, button_nebun_alb_1x
	jl button_fail24
	
	cmp eax, button_nebun_alb_1x + linie_size
	jg button_fail24
	mov eax, [ebp+arg3]
	cmp eax, button_nebun_alb_1y
	jl button_fail24
	cmp eax, button_nebun_alb_1y + linie_size
	jg button_fail24
	;s-a dat click in interiorul butonului
	
	;verificam daca nu exista pion in fata primului nebun alb
	cmp salvare5, 1
	je salvare_pozitie24
	jmp button_fail24
	
salvare_pozitie24:
	mov eax, 1
	mov salvare24, eax
	
	make_desen_nebun_alb2 '^', area, button_nebun_alb_1x +10 , button_nebun_alb_1y + 10
	coloare_zona buton_x + linie_size * 2, buton_y + linie_size * 7, linie_size, 0d48c44h
	jmp button_fail24
	
button_fail24:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam nebunul se afla in interiorul "butonului" pe care o sa il apasam 
cmp salvare25, 1
	je salvare_pozitie25
	
	mov eax, [ebp+arg2]
	cmp eax, button_nebun_alb_2x
	jl button_fail25
	
	cmp eax, button_nebun_alb_2x + linie_size
	jg button_fail25
	mov eax, [ebp+arg3]
	cmp eax, button_nebun_alb_2y
	jl button_fail25
	cmp eax, button_nebun_alb_2y + linie_size
	jg button_fail25
	;s-a dat click in interiorul butonului
	
	;verificam daca nu exista pion in fata celui de al doilea nebun alb
	cmp salvare8, 1
	je salvare_pozitie25
	jmp button_fail25
	
salvare_pozitie25:
	mov eax, 1
	mov salvare25, eax
	
	make_desen_nebun_alb '^', area, button_nebun_alb_2x +10 , button_nebun_alb_2y + 10
	coloare_zona buton_x + linie_size * 5, buton_y + linie_size * 7, linie_size, 0ffcba4h
	jmp button_fail25
	
button_fail25:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam nebunul se afla in interiorul "butonului" pe care o sa il apasam 
cmp salvare26, 1
	je salvare_pozitie26
	
	mov eax, [ebp+arg2]
	cmp eax, button_nebun_negru_1x
	jl button_fail26
	
	cmp eax, button_nebun_negru_1x + linie_size
	jg button_fail26
	mov eax, [ebp+arg3]
	cmp eax, button_nebun_negru_1y
	jl button_fail26
	cmp eax, button_nebun_negru_1y + linie_size
	jg button_fail26
	;s-a dat click in interiorul butonului
	
	;verificam daca nu exista pion in fata primului nebun negru
	cmp salvare12, 1
	je salvare_pozitie26
	jmp button_fail26

salvare_pozitie26:
	mov eax, 1
	mov salvare26, eax
	
	make_desen_nebun_negru '^', area, button_nebun_negru_1x +10 , button_nebun_negru_1y + 10
	coloare_zona buton_x + linie_size * 2, buton_y , linie_size, 0ffcba4h
	jmp button_fail26
;am terminat de mutat nebunii

;mutam regina neagra
button_fail26:
	;se trece la "click-ul urmator"
	;verificam daca coordonatele unde vrem sa mutam regina se afla in interiorul "butonului" pe care o sa il apasam 
cmp salvare27, 1
	je salvare_pozitie27
	
	mov eax, [ebp+arg2]
	cmp eax, button_regina_x1
	jl button_fail27
	
	cmp eax, button_regina_x1 + linie_size
	jg button_fail27
	mov eax, [ebp+arg3]
	cmp eax, button_regina_y1
	jl button_fail27
	cmp eax, button_regina_y1 + linie_size
	jg button_fail27
	;s-a dat click in interiorul butonului
	
	;verificam daca nu exista pion in fata reginei negre
	cmp salvare13, 1
	je salvare_pozitie27
	jmp button_fail27
	
salvare_pozitie27:
	mov eax, 1
	mov salvare27, eax
	
	make_desen_regina_neagra '^', area, button_regina_x1 +10 , button_regina_y1 + 10
	coloare_zona buton_x + linie_size * 3, buton_y , linie_size, 0d48c44h
	jmp button_fail27
;aici se termina mutarea pieselor(se pot introduce oricand noi mutari)

button_fail27:
;in cazul in care nu mai e nimic de desenat facem un jump la final pentru ca mutarile sa fie ultima parte, dupa desenarea tablei si a pieselor
	jmp final_draw
		
evt_timer:

	inc counter
	
afisare_litere:
	;afisam valoarea counter-ului curent (sute, zeci si unitati)
	mov ebx, 10
	mov eax, counter
	;cifra unitatilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 30, 10
	;cifra zecilor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 20, 10
	;cifra sutelor
	mov edx, 0
	div ebx
	add edx, '0'
	make_text_macro edx, area, 10, 10
	
;coloram partea superioara si inferiora a tablei de sah(marginile)
	;partea superioara
	coloare_zona 0 , 0, 60, 0ffcba4h
	coloare_zona 60 , 0, 60, 0ffcba4h
	coloare_zona 120 , 0, 60, 0ffcba4h
	coloare_zona 180 , 0, 60, 0ffcba4h
	coloare_zona 240 , 0, 60, 0ffcba4h
	coloare_zona 300 , 0, 60, 0ffcba4h
	coloare_zona 360 , 0, 60, 0ffcba4h
	coloare_zona 420 , 0, 60, 0ffcba4h
	coloare_zona 480 , 0, 60, 0ffcba4h
	coloare_zona 540 , 0, 60, 0ffcba4h
	coloare_zona 600 , 0, 60, 0ffcba4h
	
	;partea inferioara
	coloare_zona 0 , 620, 20, 0ffcba4h
	coloare_zona 20 , 620, 20, 0ffcba4h
	coloare_zona 40 , 620, 20, 0ffcba4h
	coloare_zona 60 , 620, 20, 0ffcba4h
	coloare_zona 80 , 620, 20, 0ffcba4h
	coloare_zona 100 , 620, 20, 0ffcba4h
	coloare_zona 120 , 620, 20, 0ffcba4h
	coloare_zona 140 , 620, 20, 0ffcba4h
	coloare_zona 160 , 620, 20, 0ffcba4h
	coloare_zona 180 , 620, 20, 0ffcba4h
	coloare_zona 200 , 620, 20, 0ffcba4h
	coloare_zona 220 , 620, 20, 0ffcba4h
	coloare_zona 240 , 620, 20, 0ffcba4h
	coloare_zona 260 , 620, 20, 0ffcba4h
	coloare_zona 280 , 620, 20, 0ffcba4h
	coloare_zona 300 , 620, 20, 0ffcba4h
	coloare_zona 320 , 620, 20, 0ffcba4h
	coloare_zona 340 , 620, 20, 0ffcba4h
	coloare_zona 360 , 620, 20, 0ffcba4h
	coloare_zona 380 , 620, 20, 0ffcba4h
	coloare_zona 400 , 620, 20, 0ffcba4h
	coloare_zona 420 , 620, 20, 0ffcba4h
	coloare_zona 440 , 620, 20, 0ffcba4h
	coloare_zona 460 , 620, 20, 0ffcba4h
	coloare_zona 480 , 620, 20, 0ffcba4h
	coloare_zona 500 , 620, 20, 0ffcba4h
	coloare_zona 520 , 620, 20, 0ffcba4h
	coloare_zona 540 , 620, 20, 0ffcba4h
	coloare_zona 560 , 620, 20, 0ffcba4h
	coloare_zona 580 , 620, 20, 0ffcba4h
	coloare_zona 600 , 620, 20, 0ffcba4h
	coloare_zona 620 , 620, 20, 0ffcba4h
	
	;stanga
	coloare_zona 0 , 60, 30, 0ffcba4h
	coloare_zona 0 , 90, 30, 0ffcba4h
	coloare_zona 0 , 120, 30, 0ffcba4h
	coloare_zona 0 , 150, 30, 0ffcba4h
	coloare_zona 0 , 180, 30, 0ffcba4h
	coloare_zona 0 , 210, 30, 0ffcba4h
	coloare_zona 0 , 240, 30, 0ffcba4h
	coloare_zona 0 , 270, 30, 0ffcba4h
	coloare_zona 0 , 300, 30, 0ffcba4h
	coloare_zona 0 , 330, 30, 0ffcba4h
	coloare_zona 0 , 360, 30, 0ffcba4h
	coloare_zona 0 , 390, 30, 0ffcba4h
	coloare_zona 0 , 420, 30, 0ffcba4h
	coloare_zona 0 , 450, 30, 0ffcba4h
	coloare_zona 0 , 480, 30, 0ffcba4h
	coloare_zona 0 , 510, 30, 0ffcba4h
	coloare_zona 0 , 540, 30, 0ffcba4h
	coloare_zona 0 , 570, 30, 0ffcba4h
	coloare_zona 0 , 600, 30, 0ffcba4h

	;dreapta
	coloare_zona 590, 60, 60, 0ffcba4h
	coloare_zona 590, 120, 60, 0ffcba4h
	coloare_zona 590, 180, 60, 0ffcba4h
	coloare_zona 590, 240, 60, 0ffcba4h
	coloare_zona 590, 300, 60, 0ffcba4h
	coloare_zona 590, 360, 60, 0ffcba4h
	coloare_zona 590, 420, 60, 0ffcba4h
	coloare_zona 590, 480, 60, 0ffcba4h
	coloare_zona 590, 540, 60, 0ffcba4h
	coloare_zona 590, 570, 60, 0ffcba4h
	
	;coloram fiecare "patratel" al tablei, inainte de a desena propriu zis patratelele, pentru a ramane conturul lor la suprafata
	;prima linie
		;maro
	coloare_zona buton_x + linie_size, buton_y, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*3, buton_y, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*5, buton_y, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*7, buton_y, linie_size, 0d48c44h
		;bej
	coloare_zona buton_x , buton_y , linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *2, buton_y , linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *4, buton_y , linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *6, buton_y , linie_size, 0ffcba4h

	;a doua linie
		;maro
	coloare_zona buton_x , buton_y + linie_size, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size *2, buton_y + linie_size, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size *4, buton_y + linie_size, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size *6, buton_y + linie_size, linie_size, 0d48c44h
		;bej
	coloare_zona buton_x + linie_size, buton_y +linie_size, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *3, buton_y+ linie_size , linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *5, buton_y+ linie_size, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *7, buton_y+ linie_size, linie_size, 0ffcba4h
	
	;a treia linie
		;maro
	coloare_zona buton_x + linie_size , buton_y + linie_size * 2, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*3 , buton_y + linie_size * 2, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*5, buton_y + linie_size * 2, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*7, buton_y + linie_size * 2, linie_size, 0d48c44h
		;bej
	coloare_zona buton_x , buton_y +linie_size*2, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *2, buton_y+ linie_size*2 , linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *4, buton_y+ linie_size*2, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *6, buton_y+ linie_size*2, linie_size, 0ffcba4h
	
	;a patra linie
		;maro
	coloare_zona buton_x  , buton_y + linie_size * 3, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size *2, buton_y + linie_size * 3, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*4, buton_y + linie_size * 3, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*6, buton_y + linie_size * 3, linie_size, 0d48c44h
		;bej
	coloare_zona buton_x +linie_size, buton_y +linie_size*3, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *3, buton_y+ linie_size*3 , linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *5, buton_y+ linie_size*3, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *7, buton_y+ linie_size*3, linie_size, 0ffcba4h
	
	;a cincea linie
		;maro
	coloare_zona buton_x + linie_size , buton_y + linie_size * 4, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*3 , buton_y + linie_size * 4, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*5, buton_y + linie_size * 4, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*7, buton_y + linie_size * 4, linie_size, 0d48c44h
		;bej
	coloare_zona buton_x , buton_y +linie_size*4, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *2, buton_y+ linie_size*4 , linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *4, buton_y+ linie_size*4, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *6, buton_y+ linie_size*4, linie_size, 0ffcba4h
	
	;a sasea linie
		;maro
	coloare_zona buton_x  , buton_y + linie_size * 5, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size *2, buton_y + linie_size *5, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*4, buton_y + linie_size * 5, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*6, buton_y + linie_size * 5, linie_size, 0d48c44h
		;bej
	coloare_zona buton_x +linie_size, buton_y +linie_size*5, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *3, buton_y+ linie_size*5 , linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *5, buton_y+ linie_size*5, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *7, buton_y+ linie_size*5, linie_size, 0ffcba4h
	
	;a saptea linie
		;maro
	coloare_zona buton_x + linie_size , buton_y + linie_size * 6, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*3 , buton_y + linie_size * 6, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*5, buton_y + linie_size * 6, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*7, buton_y + linie_size * 6, linie_size, 0d48c44h
		;bej
	coloare_zona buton_x , buton_y +linie_size*6, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *2, buton_y+ linie_size*6 , linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *4, buton_y+ linie_size*6, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *6, buton_y+ linie_size*6, linie_size, 0ffcba4h
	
	;a opta linie
		;maro
	coloare_zona buton_x  , buton_y + linie_size * 7, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size *2, buton_y + linie_size *7, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*4, buton_y + linie_size * 7, linie_size, 0d48c44h
	coloare_zona buton_x + linie_size*6, buton_y + linie_size * 7, linie_size, 0d48c44h
		;bej
	coloare_zona buton_x +linie_size, buton_y +linie_size*7, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *3, buton_y+ linie_size*7 , linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *5, buton_y+ linie_size*7, linie_size, 0ffcba4h
	coloare_zona buton_x + linie_size *7, buton_y+ linie_size*7, linie_size, 0ffcba4h
						
;asezam fiecare pion pe tabla de sah
	;prima linie tabla cu piese negre
	make_desen_turn_negru '^', area, buton_x +10 , buton_y + 10
	make_desen_cal_negru '^', area, buton_x + linie_size +10 , buton_y + 10
	make_desen_nebun_negru '^', area, buton_x + linie_size*2 +10 , buton_y + 10
	make_desen_regina_neagra '^', area, buton_x + linie_size*3 +10 , buton_y + 10
	make_desen_rege_negru '^', area, buton_x + linie_size*4 +10 , buton_y + 10
	make_desen_nebun_negru2 '^', area, buton_x + linie_size*5 +10 , buton_y + 10
	make_desen_cal_negru2 '^', area, buton_x + linie_size*6 +10 , buton_y + 10
	make_desen_turn_negru2 '^', area, buton_x + linie_size*7 +10 , buton_y + 10
	;a doua linie cu piese negre
	make_desen_pion_negru2 '^', area, buton_x +10 , buton_y + linie_size + 10
	make_desen_pion_negru '^', area, buton_x + linie_size +10 , buton_y + linie_size + 10
	make_desen_pion_negru2 '^', area, buton_x + linie_size*2 +10 , buton_y + linie_size + 10
	make_desen_pion_negru '^', area, buton_x + linie_size*3 +10 , buton_y + linie_size + 10
    make_desen_pion_negru2 '^', area, buton_x + linie_size*4 +10 , buton_y + linie_size + 10
	make_desen_pion_negru '^', area, buton_x + linie_size*5 +10 , buton_y + linie_size + 10
	make_desen_pion_negru2 '^', area, buton_x + linie_size*6 +10 , buton_y + linie_size + 10
	make_desen_pion_negru '^', area, buton_x + linie_size*7 +10 , buton_y + linie_size + 10
	
	;prima linie cu pise albe(linia 7)
	make_desen_pion_alb '^', area, buton_x +10 , buton_y + linie_size*6 + 10
	make_desen_pion_alb2 '^', area, buton_x + linie_size +10 , buton_y + linie_size*6 + 10
	make_desen_pion_alb '^', area, buton_x + linie_size*2 +10 , buton_y + linie_size*6 + 10
	make_desen_pion_alb2 '^', area, buton_x + linie_size*3 +10 , buton_y + linie_size*6 + 10
    make_desen_pion_alb '^', area, buton_x + linie_size*4 +10 , buton_y + linie_size*6 + 10
	make_desen_pion_alb2 '^', area, buton_x + linie_size*5 +10 , buton_y + linie_size*6 + 10
	make_desen_pion_alb '^', area, buton_x + linie_size*6 +10 , buton_y + linie_size*6 + 10
	make_desen_pion_alb2 '^', area, buton_x + linie_size*7 +10 , buton_y + linie_size*6 + 10
	;a doua linie cu piese albe(linia 8)
	make_desen_turn_alb2 '^', area, buton_x +10 , buton_y +  linie_size*7 + 10
	make_desen_cal_alb '^', area, buton_x + linie_size +10 , buton_y +  linie_size*7+ 10
	make_desen_nebun_alb2 '^', area, buton_x + linie_size*2 +10 , buton_y +  linie_size*7 + 10
	make_desen_regina_alba '^', area, buton_x + linie_size*3 +10 , buton_y +  linie_size*7 + 10
	make_desen_rege_alb '^', area, buton_x + linie_size*4 +10 , buton_y +  linie_size*7+ 10
	make_desen_nebun_alb '^', area, buton_x + linie_size*5 +10 , buton_y+  linie_size*7 + 10
	make_desen_cal_alb2 '^', area, buton_x + linie_size*6 +10 , buton_y +  linie_size*7+ 10
	make_desen_turn_alb '^', area, buton_x + linie_size*7 +10 , buton_y +  linie_size*7+ 10
	
	
	
final_draw:
;desenam patrat cu patrat tabla de sah
	;primul rand din tabla
	patrat buton_x,buton_y,linie_size,0d48c44h
	patrat buton_x + linie_size,buton_y,linie_size,0d48c44h
	patrat buton_x + linie_size*2,buton_y,linie_size,0d48c44h
	patrat buton_x + linie_size*3,buton_y,linie_size,0d48c44h
	patrat buton_x + linie_size*4,buton_y,linie_size,0d48c44h
	patrat buton_x + linie_size*5,buton_y,linie_size,0d48c44h
	patrat buton_x + linie_size*6,buton_y,linie_size,0d48c44h
	patrat buton_x + linie_size*7,buton_y,linie_size,0d48c44h

	;al doile rand din tabla
	patrat buton_x,buton_y + linie_size,linie_size,0d48c44h
	patrat buton_x+linie_size,buton_y + linie_size,linie_size,0d48c44h
	patrat buton_x+linie_size*2,buton_y + linie_size,linie_size,0d48c44h
	patrat buton_x+linie_size*3,buton_y + linie_size,linie_size,0d48c44h
	patrat buton_x+linie_size*4,buton_y + linie_size,linie_size,0d48c44h
	patrat buton_x+linie_size*5,buton_y + linie_size,linie_size,0d48c44h
	patrat buton_x+linie_size*6,buton_y + linie_size,linie_size,0d48c44h
	patrat buton_x+linie_size*7,buton_y + linie_size,linie_size,0d48c44h
	
	;al treilea rand din tabla
	patrat buton_x,buton_y + linie_size*2,linie_size,0d48c44h
	patrat buton_x + linie_size,buton_y + linie_size*2,linie_size,0d48c44h
	patrat buton_x + linie_size * 2,buton_y + linie_size*2,linie_size,0d48c44h
	patrat buton_x + linie_size *3,buton_y + linie_size*2,linie_size,0d48c44h
	patrat buton_x + linie_size*4,buton_y + linie_size*2,linie_size,0d48c44h
	patrat buton_x + linie_size*5,buton_y + linie_size*2,linie_size,0d48c44h
	patrat buton_x + linie_size*6,buton_y + linie_size*2,linie_size,0d48c44h
	patrat buton_x + linie_size*7,buton_y + linie_size*2,linie_size,0d48c44h
	
	;al patrulea rand din tabla
	patrat buton_x,buton_y + linie_size*3,linie_size,0d48c44h
	patrat buton_x + linie_size,buton_y + linie_size*3,linie_size,0d48c44h
	patrat buton_x + linie_size * 2,buton_y + linie_size*3,linie_size,0d48c44h
	patrat buton_x + linie_size *3,buton_y + linie_size*3,linie_size,0d48c44h
	patrat buton_x + linie_size*4,buton_y + linie_size*3,linie_size,0d48c44h
	patrat buton_x + linie_size*5,buton_y + linie_size*3,linie_size,0d48c44h
	patrat buton_x + linie_size*6,buton_y + linie_size*3,linie_size,0d48c44h
	patrat buton_x + linie_size*7,buton_y + linie_size*3,linie_size,0d48c44h
	
	;al cincilea rand din tabla
	patrat buton_x,buton_y + linie_size*4,linie_size,0d48c44h
	patrat buton_x + linie_size,buton_y + linie_size*4,linie_size,0d48c44h
	patrat buton_x + linie_size * 2,buton_y + linie_size*4,linie_size,0d48c44h
	patrat buton_x + linie_size *3,buton_y + linie_size*4,linie_size,0d48c44h
	patrat buton_x + linie_size*4,buton_y + linie_size*4,linie_size,0d48c44h
	patrat buton_x + linie_size*5,buton_y + linie_size*4,linie_size,0d48c44h
	patrat buton_x + linie_size*6,buton_y + linie_size*4,linie_size,0d48c44h
	patrat buton_x + linie_size*7,buton_y + linie_size*4,linie_size,0d48c44h
	
	;al saselea rand din tabla
	patrat buton_x,buton_y + linie_size*5,linie_size,0d48c44h
	patrat buton_x + linie_size,buton_y + linie_size*5,linie_size,0d48c44h
	patrat buton_x + linie_size * 2,buton_y + linie_size*5,linie_size,0d48c44h
	patrat buton_x + linie_size *3,buton_y + linie_size*5,linie_size,0d48c44h
	patrat buton_x + linie_size*4,buton_y + linie_size*5,linie_size,0d48c44h
	patrat buton_x + linie_size*5,buton_y + linie_size*5,linie_size,0d48c44h
	patrat buton_x + linie_size*6,buton_y + linie_size*5,linie_size,0d48c44h
	patrat buton_x + linie_size*7,buton_y + linie_size*5,linie_size,0d48c44h
	
	;al saptelea rand din tabla
	patrat buton_x,buton_y + linie_size*6,linie_size,0d48c44h
	patrat buton_x + linie_size,buton_y + linie_size*6,linie_size,0d48c44h
	patrat buton_x + linie_size * 2,buton_y + linie_size*6,linie_size,0d48c44h
	patrat buton_x + linie_size *3,buton_y + linie_size*6,linie_size,0d48c44h
	patrat buton_x + linie_size*4,buton_y + linie_size*6,linie_size,0d48c44h
	patrat buton_x + linie_size*5,buton_y + linie_size*6,linie_size,0d48c44h
	patrat buton_x + linie_size*6,buton_y + linie_size*6,linie_size,0d48c44h
	patrat buton_x + linie_size*7,buton_y + linie_size*6,linie_size,0d48c44h
	
	;al optulea rand din tabla
	patrat buton_x,buton_y + linie_size*7,linie_size,0d48c44h
	patrat buton_x + linie_size,buton_y + linie_size*7,linie_size,0d48c44h
	patrat buton_x + linie_size * 2,buton_y + linie_size*7,linie_size,0d48c44h
	patrat buton_x + linie_size *3,buton_y + linie_size*7,linie_size,0d48c44h
	patrat buton_x + linie_size*4,buton_y + linie_size*7,linie_size,0d48c44h
	patrat buton_x + linie_size*5,buton_y + linie_size*7,linie_size,0d48c44h
	patrat buton_x + linie_size*6,buton_y + linie_size*7,linie_size,0d48c44h
	patrat buton_x + linie_size*7,buton_y + linie_size*7,linie_size,0d48c44h

;scriem literele si cifrele in jurul tablei de sah
	;partea de sus
	make_text_macro 'A', area, buton_x + 20, buton_y-20
	make_text_macro 'B', area, buton_x + 20 + linie_size, buton_y-20
	make_text_macro 'C', area, buton_x + 20 + linie_size*2, buton_y-20
	make_text_macro 'D', area, buton_x + 20 + linie_size*3, buton_y-20
	make_text_macro 'E', area, buton_x + 20 + linie_size*4, buton_y-20
	make_text_macro 'F', area, buton_x + 20 + linie_size*5, buton_y-20
	make_text_macro 'G', area, buton_x + 20 + linie_size*6, buton_y-20
	make_text_macro 'H', area, buton_x + 20 + linie_size*7, buton_y-20
	
	;partea de jos
	make_text_macro 'A', area, buton_x + 20, buton_y+560
	make_text_macro 'B', area, buton_x + 20 + linie_size, buton_y+560
	make_text_macro 'C', area, buton_x + 20 + linie_size*2, buton_y+560
	make_text_macro 'D', area, buton_x + 20 + linie_size*3, buton_y+560
	make_text_macro 'E', area, buton_x + 20 + linie_size*4, buton_y+560
	make_text_macro 'F', area, buton_x + 20 + linie_size*5, buton_y+560
	make_text_macro 'G', area, buton_x + 20 + linie_size*6, buton_y+560
	make_text_macro 'H', area, buton_x + 20 + linie_size*7, buton_y+560
	
	;stanga
	make_text_macro '1', area, buton_x -15 , buton_y+20
	make_text_macro '2', area, buton_x -15 , buton_y+20 + linie_size
	make_text_macro '3', area, buton_x -15 , buton_y+20 + linie_size*2
	make_text_macro '4', area, buton_x -15 , buton_y+20 + linie_size*3
	make_text_macro '5', area, buton_x -15 , buton_y+20 + linie_size*4
	make_text_macro '6', area, buton_x -15 , buton_y+20 + linie_size*5
	make_text_macro '7', area, buton_x -15 , buton_y+20 + linie_size*6
	make_text_macro '8', area, buton_x -15 , buton_y+20 + linie_size*7
	
	;dreapta
	make_text_macro '1', area, buton_x + 562 , buton_y+20
	make_text_macro '2', area, buton_x + 562 , buton_y+20 + linie_size
	make_text_macro '3', area, buton_x + 562 , buton_y+20 + linie_size*2
	make_text_macro '4', area, buton_x + 562 , buton_y+20 + linie_size*3
	make_text_macro '5', area, buton_x + 562 , buton_y+20 + linie_size*4
	make_text_macro '6', area, buton_x + 562 , buton_y+20 + linie_size*5
	make_text_macro '7', area, buton_x + 562 , buton_y+20 + linie_size*6
	make_text_macro '8', area, buton_x + 562 , buton_y+20 + linie_size*7
					;END TABLA DE SAH
					
	;scriem titlul jocului
	make_text_macro 'C', area, 100, 10
	make_text_macro 'H', area, 110, 10
	make_text_macro 'E', area, 120, 10
	make_text_macro 'S', area, 130, 10
	make_text_macro 'S', area, 140, 10


	popa
	mov esp, ebp
	pop ebp
	ret
draw endp
start:

;alocam memorie pentru zona de desenat (marimea zonei de 640 x 640)
	mov eax, area_width
	mov ebx, area_width
	mul ebx ;inmultim area_width cu area_width
	shl eax, 2 ;rezultatul il inmultim cu 4(deoarece fiecare pixel din memorie ocupa cate 4 byte)
	push eax
	call malloc ;se aloca memorie
	add esp, 4
	mov area, eax ; area este zona de desenare
	
;apelam functia de desenare a ferestrei
; typedef void (*DrawFunc)(int evt, int x, int y);
; void __cdecl BeginDrawing(const char *title, int width, int height, unsigned int *area, DrawFunc draw);
	
	push offset draw ;prin intermediul procedurii draw putem desena( aceasta se apeleaza la fiecare eveniment)
	push area
	push area_height
	push area_width
	push offset window_title
	call BeginDrawing
	add esp, 20
	

;terminarea programului
	push 0
	call exit
end start