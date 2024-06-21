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

        # Server details
        server_username="was"
        server_ip="192.168.178.239"
        server_port="23"
        server_path="/home/was/bizweb_api/b2b_doc"

        # Copy file to server
        scp -P "$server_port" "$new_file" "$server_username@$server_ip:$server_path"
        ;;
    * )
        echo "File not copied to the server. Please build the file first."
        ;;
esac
