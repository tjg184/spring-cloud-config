
## To Run

Start Minio
`docker run -d -v data:/data -p 9000:9000 -p 9001:9001 minio/minio server /data --console-address ":9001"`

Add access key and secret (AWS_ACCESS_KEY_ID=?;AWS_REGION=us-east-1;AWS_SECRET_ACCESS_KEY=?)
Create a bucket `bucket1`
Create a folder `master` in this bucket and add `data1.json` file

## Git

`curl localhost:8080/name/default/master/data1.json`

## S3

`curl localhost:8080/name/default/master/data1.json` (doesn't work)
`curl localhost:8080/data1/default/master` (works)
