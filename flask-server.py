from flask import Flask, render_template, Response
import cv2

app = Flask(__name__)

@app.route('/')
def index():
    return "ok boss"

def gen():
    while True:
        with open("image.jpg", "rb") as f:
            check_chars = f.read()[-2:]
        if check_chars != b'\xff\xd9':
            print("Corrupted image ok")
        else:
            img = cv2.imread("image.jpg", cv2.IMREAD_COLOR)
            if (img is not None):
                result, epic = cv2.imencode('.jpg', img, [int(cv2.IMWRITE_JPEG_QUALITY),90])
                frame = epic.tobytes()
                yield (b'--frame\r\n'
                b'Content-Type: image/jpeg\r\n\r\n' + frame + b'll\r\n')

@app.route('/video_feed')
def video_feed():
    return Response(gen(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port="5802", debug=True)
