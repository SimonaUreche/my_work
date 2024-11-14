import cv2 as cv
import time
import datetime
import pygame  #pentru alertă sonoră

pygame.mixer.init() #initializarea sistemului de sunet
pygame.mixer.music.load("alert.wav")  

capture = cv.VideoCapture(0) #argumentul 0 indica folosirea camerei implicite de la computer

detection = False #o folosesc pt a semnala daca se inregistreaza sau nu
detection_stopped_time = None
timer_started = False #o folosesc pentru a semnaliza ca timerul pentru inregistrare a inceput
SECONDS_TO_RECORD_AFTER_DETECTION = 1

frame_size = (int(capture.get(3)), int(capture.get(4)))
fourcc = cv.VideoWriter_fourcc(*"mp4v") #formatul fisierului

# am folosit frame differecing care presupune compararea fiecarui cadru video cu urmatorul => 
# => identificam astfel obiectele in miscare
ret, prev_frame = capture.read() #in ret o sa retinem daca avem un cadru valid(True/False), iar in prev_frame avem un cadru capturat
prev_gray = cv.cvtColor(prev_frame, cv.COLOR_BGR2GRAY) 
prev_gray = cv.GaussianBlur(prev_gray, (21, 21), 0)  #facem imaginea gri si adaugam un blur pentru a reduce zgomotul
#este nevoie sa adaugam un blur pentru reducerea zgomotului, astfel pixelii care difera usor intre 2 imagini consecutive sa nu reprezinte o miscare reala(de ex variatii de lumina/intensitate)


while True:
    _, frame = capture.read()
    gray = cv.cvtColor(frame, cv.COLOR_BGR2GRAY)
    gray = cv.GaussianBlur(gray, (21, 21), 0)  

    frame_diff = cv.absdiff(prev_gray, gray) #este calculata diferenta absoluta intre cadrul curent si cel anterior
    _, thresh = cv.threshold(frame_diff, 25, 255, cv.THRESH_BINARY) #creez o imagine binara pe baza diferentei, iar pe urma ii fac conturul mai vizibil
    thresh = cv.dilate(thresh, None, iterations=2)

    contours, _ = cv.findContours(thresh, cv.RETR_EXTERNAL, cv.CHAIN_APPROX_SIMPLE) #cauta contururi in imaginea binara dilatata pt a detecta miscare
    
    motion_detected = False
    for contour in contours: #daca miscarea detectata e mai mare decat 1300 de pixeli
        if cv.contourArea(contour) < 1300:  #pentru fiecare valoare din contur verificam daca nu cumva e o miscare prea slaba
            continue
        motion_detected = True
        (x, y, w, h) = cv.boundingRect(contour)
        cv.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 3)  # Desenează un dreptunghi în jurul mișcării

    if motion_detected:
        if not detection:
            # Începe o nouă sesiune de înregistrare
            detection = True
            current_time = datetime.datetime.now().strftime("%d-%m-%Y-%H-%M-%S")
            out = cv.VideoWriter(f"C:\\Users\\Simona\\OneDrive\\Desktop\\SSC\\Home-surveillance-system\\videos\\{current_time}.mp4", fourcc, 20, frame_size)
            print("Started Recording!")
            pygame.mixer.music.play()  

        detection_stopped_time = time.time() #retinem ultimul moment in care s-a detectat miscare
        timer_started = False 
    elif detection:
        if timer_started:
            if time.time() - detection_stopped_time >= SECONDS_TO_RECORD_AFTER_DETECTION:
                detection = False
                timer_started = False
                out.release()
                print('Stop Recording!')
                pygame.mixer.music.stop() 
        else:
           
            timer_started = True #il pornim pt a putea contoriza timpul pana la oprire
            detection_stopped_time = time.time()

    if detection: #daca tot avem miscare se tot scriu cadre video
        out.write(frame)

    cv.imshow("Camera", frame)

    prev_gray = gray.copy() #actualizarea cadrului anterior

    if cv.waitKey(1) == ord('q'):
        break

#Oprirea resurselor
if detection:
    out.release()
capture.release()
cv.destroyAllWindows()
pygame.mixer.quit()
