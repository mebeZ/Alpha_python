import numpy as np
import cv2
from networktables import NetworkTables

cap = cv2.VideoCapture("http://10.11.55.11:5800/video")

while(True):
    # Capture frame-by-frame
    ret, frame = cap.read()
    NetworkTables.initialize(server="10.11.55.2")
    table = NetworkTables.getTable("limelight")
    tx = table.getNumber('tx0',None)
    ty = table.getNumber('ty0',None)
    ta = table.getNumber('ta0',None)
    ts = table.getNumber('ts0',None)

    print(ta)

    # Display the resulting frame
    cv2.imshow('frame',frame)
    if cv2.waitKey(1)& 0xFF == ord('q'):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()

