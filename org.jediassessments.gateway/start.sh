export NOW=$(date '+%Y%m%d%H%M%S')
echo $NOW
docker-compose build --force-rm --parallel
docker-compose up -d
#nbbuilders=$(docker images --format "{{.ID}}" --filter label=stage=builder | wc -l)
#echo $nbbuilders
#if [ $nbbuilders -gt 1 ]
#then
	docker image prune -f --filter label=stage=builder --filter label!=version=$NOW
#fi
docker image prune -f --filter label=stage=runner
