cd ..
mvn exec:java &
MyPID=$!                        # You sign it's PID
echo $MyPID                     # You print to terminal
echo "kill $MyPID" > scripts/stop.bat  # Write the the command kill pid in stop.bat
cd scripts