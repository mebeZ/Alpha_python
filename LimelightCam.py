import numpy as np
import cv2
from networktables import NetworkTables

class LimelightCam():
    def __init__(self): #connects to limelight server
        self.cap   = cv2.VideoCapture("http://10.11.55.11:5800/video")
        self.mid_x = 160 # x screen resolution / 2
        self.mid_y = 120 # y screen resolution / 2

    def isFacingLeft(self, table, h): # determines of contour is facing left
        return table.getNumber("ts" + str(h),None) > -45:

    def addIndexs(self, arr):
        for i, el in enumerate(arr):
            newArr[i] = (i, el)
    def sortWithIndex(self, txs): # sorting contours by tx
        sortedTxs = txs.copy()
        self.addIndexs(sortedTxs)
        def compare(pair):
            if self.isContour(self.getVal(pair)):
                return self.getVal(pair)
            return 1000 # pushes non-contours off to the side
        sortedTxs.sort(key = compare)
        return sortedTxs
    def isContour(self, tx): # given tx determines if it is a contour
        return tx != 0
    def getVal(self, pair):
        return pair[1]
    def getIndex(self, pair):
        return pair[0]
    def center(table, i1, i2):
        x = (table.getNumber("tx" + str(i1),None) + table.getNumber("tx" + str(i2),None)) / 2
        y = (table.getNumber("ty" + str(i1),None) + table.getNumber("ty" + str(i2),None)) / 2
        x_coord = float(self.mid_x * (1 + x))
        y_coord = float(self.mid_y * (1 - y)) # converts offset into tangible coordinates
        return x_coord, y_coord
        
        
    def getCrosshairs(self): 
        NetworkTables.initialize(server="10.11.55.2") # gets contours
        table = NetworkTables.getTable("limelight")
        ret, frame = self.cap.read()
        tx0 = table.getNumber('tx0',None) 
        tx1 = table.getNumber('tx1',None) 
        tx2 = table.getNumber('tx2',None)
        sortedConts = self.sortWithIndex([tx0,tx1,tx2])
        leftTx  = self.getVal(sortedConts[0])
        midTx   = self.getVal(sortedConts[1])
        rightTx = self.getVal(sortedConts[2])
        leftI   = self.getIndex(sortedConts[0])
        midI    = self.getIndex(sortedConts[1])
        rightI  = self.getIndex(sortedConts[2])

        leftPair  = self.isContour(leftTx)  and self.isContour(midTx) and self.isFacingLeft(table,midI)
        rightPair = self.isContour(rightTx) and self.isContour(midTx) and not(self.isFacingLeft(table,midI))

        table.putBoolean("sawHatch",leftPair or rightPair)
        
        if leftPair:
            return center(table,leftI,midI)
        else:
            return center(table,rightI,midI)
        
    
    def getFrames(self):
        NetworkTables.initialize(server="10.11.55.2") 
        table = NetworkTables.getTable("limelight")
        x_coord, y_coord = self.getCrosshairs()
        ret, frame = self.cap.read()
        cv2.line(camera, (int(x_coord)+15, int(y_coord)), (int(x_coord)-15, int(y_coord)), (255, 0, 0), thickness=3, lineType=8)
        cv2.line(camera, (int(x_coord), int(y_coord)+15), (int(x_coord), int(y_coord)-15), (255, 0, 0), thickness=3, lineType=8)
        ret, epic = cv2.imencode('.jpg', frame, [int(cv2.IMWRITE_JPEG_QUALITY),90])
        return epic.tobytes()

    def checkForHatch(self):
        NetworkTables.initialize(server="10.11.55.2") 
        table = NetworkTables.getTable("limelight")
        return table.getBoolean("sawHatch", False)
    
