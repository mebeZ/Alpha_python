import socket
import cv2

cap = cv2.VideoCapture("http://10.11.55.11:5800/video")
ret, frame = cap.read()

HOST = '127.0.0.1'                 # Symbolic name meaning all available interfaces
PORT = 1234              # Arbitrary non-privileged port
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

s.bind((HOST, PORT))
print("binded to host")
s.listen(1)
conn, addr = s.accept()
print ('Connected by', addr)
while True:
    #data = conn.recv(1024)
    #if not data: break
    conn.sendall(frame.tobytes())
