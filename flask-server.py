from flask import Flask, render_template, Response
import cv2
from LimelightCam import LimelightCam
app = Flask(__name__)

@app.route('/')
def index():
    return "ok boss"

def gen():
    camera = LimelightCam()
    while True:
        frame = camera.getFrames()
        print(camera.checkForHatch())
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame + b'll\r\n')

@app.route('/video_feed')
def video_feed():
    return Response(gen(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5802, debug=True)
