#!/bin/bash

# Server details
server_username="lmsdev"
server_ip="192.168.178.239"
server_port="24"
server_script_directory="/home/lmsdev/was/b2b_doc"

# SSH into the server and run the stop script
ssh -p "$server_port" "$server_username@$server_ip" "bash -s" << EOF
cd "$server_script_directory"
./stopWAS_b2bdocument.sh
EOF

# SSH into the server again and run the start script
ssh -p "$server_port" "$server_username@$server_ip" "bash -s" << EOF
cd "$server_script_directory"
./startWAS_b2bdocument.sh
EOF
