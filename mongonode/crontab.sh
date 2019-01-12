0 1 * * * mongodump --out /var/backups/mongobackups/MongoDBDump.`date +"%Y_%m_%d_%I_%M_%p"` --host mongodbnode:27017
0 1 * * * find /var/backups/mongobackups/ -mtime +5 -exec rm -rf {} \;
# Mandatory blank line
