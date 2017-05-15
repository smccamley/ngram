# ngram
### Usage
Make a request
```
POST http://url:80/api/ngram/word
{
	"word" : "the"
}
```
You will get the response
```
{
  "count": 927838975,
  "relative": 4.762315882559723e-17,
  "year": "2008"
}
```
### Installation
Create a new aws instance with:
- Image Amazon Linux AMI
- Type t2.medium
- Inbound rule port 80 / anywhere.
- Inbound rule ssh / my ip.

Install docker on the instance according to the [Docker Basics guide](http://docs.aws.amazon.com/AmazonECS/latest/developerguide/docker-basics.html#install_docker).<br>
Execute a script with the content
```
docker stop ngram
docker rm ngram
docker run -p 80:80 --name ngram --memory=3g -d wilmersdorf/ngram:0.1
```
The image will need about 1 minute to start and load all ngrams into memory.
