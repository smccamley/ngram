docker stop ngram
docker rm ngram
docker run -p 80:80 --name ngram --memory=3g -d wilmersdorf/ngram:0.1
