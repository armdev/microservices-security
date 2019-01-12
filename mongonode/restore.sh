#!/usr/bin/bash

#Find most recent archive and decompress it
#find . -name \*.tar.\* -execdir sh -c "echo -n \"\${PWD} \"; ls -t *.tar.* | head -n 1" \; | sort -u -k1,1 | xargs tar -cvzf $(pwd)/Backup_mongodb/

find Backup_mongodb/ -name `ls -t Backup_mongodb/ | head -1` -type f -exec tar -xzvf {} -C $(pwd)/Restore/ \; && find Restore/ -name "*.tar.gz" -exec rm {} \;

##### Restoring MongoDB databases #####
sudo docker run --rm -v $(pwd)/Restore/Backup_mongodb:/backup --network mongodb_docker_marketplacenetwork --link mongodbnode:mongo mongo:4 bash -c 'mongorestore /backup --host mongodbnode:27017'

#####Done successfully#####
