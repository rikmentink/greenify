#!/bin/bash

# Set the right URLs here.
FRONTEND_URL="https://www.greenit.com"
BACKEND_URL="https://api.greenit.com"

# Request interval in seconds.
INTERVAL=300

# Function to fetch the URLs and log the requests
fetch_url() 
{
  local url=$1
  local time=$(date +"%H:%M:%S")
  printf "%s: Fetching %s..." "$time" "$url"

  # Fetch the URL and log the result
  if curl -s "$url" > /dev/null; then
    printf "   Done!\n"
  else
    printf "   Failed!\n"
  fi
}

# Infinite loop to keep fetching the URLs at the specified interval
while true; do
  fetch_url $FRONTEND_URL
  fetch_url $BACKEND_URL

  echo "-------------------------------------------------------"
  for ((i=1; i<=$INTERVAL; i++)); do
    sleep 1
    percentage=$((i * 100 / INTERVAL))
    printf "\rNext request in %d seconds (%d%%)" $((INTERVAL - i)) $percentage
  done
  printf "\r\033[K"
done