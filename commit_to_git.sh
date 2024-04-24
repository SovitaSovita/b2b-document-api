#!/bin/bash

# Function to add updated files to the GitHub repository
add_to_github() {
    local commit_message="$1"

    # Fetch changes from main branch
    git fetch origin main

    # Check for conflicts
    if git merge-base --is-ancestor HEAD origin/main; then
        echo "No conflicts found."
    else
        echo "Conflicts found. Please resolve conflicts before proceeding."
        git diff --name-only --diff-filter=U
        exit 1
    fi

    # Check if there are any changes to commit
    if ! git diff-index --quiet HEAD --; then
        # List updated files
        echo ""
        echo "Files updated:"
        git status --short

        # Prompt user to confirm if they want to push changes
        read -p "Do you want to push these changes? (y/n): " choice
        case "$choice" in
            [Yy]* )
                # Add all modified and new files
                git add .

                # Commit changes
                git commit -m "$commit_message"

                # Push changes to GitHub
                if git push origin main; then
                    echo "Changes pushed successfully."
                else
                    echo "Failed to push changes to GitHub."
                fi
                ;;
            * )
                echo "Changes not pushed."
                ;;
        esac
    else
        echo "No changes to commit."
    fi
}

# Call the function with the commit message
add_to_github "Update: Added new feature"
