# Installation instructions

## Create a config.json file and place it in src/main/resources/static

The config.json file must have the folloving structure


    {
      "base_url": "Your workflow manager host url",
      "token": "Your API token",
      "user_id": "The corresponding user id"
    }

## Run the project in Docker

Build the docker image

    docker build -t wm-api-demo .

Run the container

    docker run -d -p 8080:8080 wm-api-demo

# Test the application

Point your browser to {your host url}:{your port}



