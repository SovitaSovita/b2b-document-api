#!/bin/bash

# Prompt user if they have built the file
read -p "Have you built the file (Y/N)? " build_choice
case "$build_choice" in
    [Yy]* )
        # Local file path
        local_file="build/libs/b2b-document-v4-0.0.1-SNAPSHOT.jar"

        # Rename the file
        new_file="build/libs/b2b-document-v4.jar"
        mv "$local_file" "$new_file"

        # Check if the rename was successful
        if [ $? -ne 0 ]; then
            echo "Failed to rename the file."
            exit 1
        fi

        # Server details
        server_username="was"
        server_ip="192.168.178.239"
        server_port="23"
        server_path="/home/was/bizweb_api/b2b_doc"

        # Copy file to server
        scp -P "$server_port" "$new_file" "$server_username@$server_ip:$server_path"

        # Check if the scp command was successful
        if [ $? -ne 0 ]; then
            echo "Failed to copy the file to the server."
            exit 1
        fi

        # Run the Java file with nohup on the server
        ssh -p "$server_port" "$server_username@$server_ip" "cd $server_path && nohup java -jar b2b-document-v4.jar &"

        # Check if the ssh command was successful
        if [ $? -ne 0 ]; then
            echo "Failed to run the Java file on the server."
            exit 1
        fi

        # Success message
        echo "File successfully copied and Java application started on the server."
        ;;
    * )
        echo "File not copied to the server. Please build the file first."
        ;;
esac
