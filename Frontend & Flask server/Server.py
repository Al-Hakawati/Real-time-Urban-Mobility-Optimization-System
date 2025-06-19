from flask import Flask, request, jsonify, render_template
from flask_cors import CORS
import os, requests

app = Flask(__name__)
CORS(app)

HOST          = "6dff-109-237-204-140.ngrok-free.app"          
NGROK_LOGIN_URL   = f"https://{HOST}/api/operators/login"
OPERATOR_INFO_URL = f"https://{HOST}/api/operators/info/OP-20"
TRAFFIC_API_BASE  = f"https://{HOST}/api/traffic-lights/info"  

@app.route("/")
def login_page():
    return render_template("login.html")

@app.route("/welcome")
def welcome():
    return render_template("welcome.html")

@app.route("/login", methods=["POST"])
def login():
    payload = request.get_json(silent=True) or {}
    if not payload.get("operatorId") or not payload.get("password"):
        return jsonify({"ok": False, "error": "Missing credentials"}), 400

    try:
        up = requests.post(NGROK_LOGIN_URL, json=payload, timeout=6)
    except requests.RequestException as exc:
        return jsonify({"ok": False, "error": f"Upstream unreachable: {exc}"}), 502

    if up.status_code == 200:
        return jsonify({"ok": True})
    elif up.status_code == 401:
        return jsonify({"ok": False, "error": "Invalid operator ID or password"}), 401
    return jsonify({"ok": False, "error": f"Upstream error {up.status_code}"}), 502

@app.route("/operator-info")
def operator_info():
    try:
        j = requests.get(OPERATOR_INFO_URL, timeout=6).json()
        return jsonify({"name": j.get("name"), "operatorId": j.get("operatorId")})
    except Exception as exc:
        return jsonify({"error": "Failed to fetch operator", "details": str(exc)}), 500

@app.route("/traffic-info/<traffic_id>")
def traffic_info(traffic_id):
    url = f"{TRAFFIC_API_BASE}/{traffic_id}"
    try:
        r = requests.get(url, timeout=6)
        if r.status_code == 404:
            return jsonify({"error": f"{traffic_id} not found"}), 404
        r.raise_for_status()
        tl = r.json()            
        return jsonify({
            "location":                 tl.get("location"),
            "currentStatus":            tl.get("currentStatus"),
            "numberOfWaitingVehicles":  tl.get("numberOfWaitingVehicles"),
            "averageAreaSpeed":         tl.get("averageAreaSpeed"),
            "averageAreaVolume":        tl.get("averageAreaVolume"),
            "averageAreaWaitingTime":   tl.get("averageAreaWaitingTime")
        })
    except Exception as exc:
        return jsonify({"error": f"Failed to fetch {traffic_id}", "details": str(exc)}), 500

if __name__ == "__main__":
    app.run(debug=True, use_reloader=False)   
