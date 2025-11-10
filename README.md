# Android Voice Assistant (Ava)

Experimental Android voice assistant for [Home Assistant][homeassistant] that uses the [ESPHome][esphome] protocol.

Intended for turning your existing Android wall panel or similar into a local voice assistant for controlling your smart home using Home Assistant.

Requires Android 8 or above.

# Implemented
- Local wake word detection using microWakeWord models
- Streaming of voice commands
- Announcements and conversations
- Wake/preannounce sounds
- Stop word support

# Todo
- Timers
- Multiple wake word support

# Development
1. Clone the repo
2. Open in Android Studio

# Debugging on an emulated device
1. Telnet into the device, from a terminal run `telnet localhost 5554`
2. Enter your auth token if required
3. Add a redirect for the server port (default 6053) to allow incoming connections to be routed to the device, run `redir add tcp:6053:6053` 

## Connecting to Home Assistant
The device should be auto-discovered by Home Assistant as an ESPHome device in "Settings" -> "Devices and Services", click add to add it.
If not, manually add it
1. In Home Assistant, go to "Settings" -> "Device & services"
2. Click the "Add integration" button
3. Choose "ESPHome" and then "Set up another instance of ESPHome"
4. Enter the IP address of your voice satellite with port 6053
5. Click "Submit"

<!-- Links -->
[homeassistant]: https://www.home-assistant.io/
[esphome]: https://esphome.io/
