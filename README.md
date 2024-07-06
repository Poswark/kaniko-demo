# kaniko-demo
Build images with kaniko 

https://itnext.io/building-docker-images-with-kaniko-6859bdb893f6


$ cd docker-hello-world-spring-boot
$
$ docker run --rm \
    -v /Users/giovannyorjuelamelo/Documents/Laboratorio/git/kaniko-demo:/workspace \
    gcr.io/kaniko-project/executor:debug \
    --context "/workspace" \
    --dockerfile "/workspace/Dockerfile" \
    --destination poswark/kaniko-demo:1.0.1 --verbosity info --kaniko-dir /tmp --log-format json --label commit


docker run -v `pwd`:/workspace/ gcr.io/kaniko-project/executor:latest --no-push --dockerfile /workspace/Dockerfile  --context /workspace/ --verbosity debug --destination=image:tag --tarPath=/kaniko-workspace/image.tar --kaniko-dir /tmp





docker run -v /var/run/docker.sock:/var/run/docker.sock -v $(pwd):/workspace -v ~/.docker/config.json:/kaniko/.docker/config.json gcr.io/kaniko-project/executor:latest --dockerfile /workspace/Dockerfile \
--destination poswark/kaniko-demo \ 
-e "TMPDIR=/tmp" \
--context dir:///workspace/ \  
--verbosity debug

