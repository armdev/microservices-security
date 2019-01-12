#!/usr/bin/bash

##### Create Backup folders #####

mkdir -p ~/Documents/MongoDBDump_Backup

##### Dumping MongoDB databases #####
sudo docker run -d -v $(pwd)/Backup_mongodb/:/backup --network mongodb_docker_marketplacenetwork --link mongodbnode:mongo mongo:4 bash -c 'mongodump --out /backup --host mongodbnode:27017'

##### Archiving dump files #####
tar -cvzf Backup_mongodb/MongoDBDump.`date +"%Y_%m_%d_%I_%M_%p"`.tar.gz ./Backup_mongodb/
sudo rm -rf Backup_mongodb/adm*

##### Sync with backup directory #####
rsync -vau --delete-after Backup_mongodb/ ~/Documents/MongoDBDump_Backup/

##### Keep recent 5 day archives #####
find Backup_mongodb/Mongo* -mtime +5 -exec rm {} \;
#####Done successfully#####
