docker run -d -p 222:22 -p 7331:7331 --name arachni -e ARACHNI_SERVER_ROOT_PASSWORD="DockerArachniPWD" -e ARACHNI_PARAMS="--authentication-username arachni --authentication-password Pass123 --only-positives"  arachni/arachni:latest
