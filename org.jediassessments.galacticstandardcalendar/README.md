docker image build \
	--force-rm 
	--network=host 
	-f src/main/docker/Dockerfile 
	-t jediassessments/galacticstandardcalendar 
	--build-arg BUILDCACHEVERSION="$(date '+%Y%m%d%H%M%S')"
	--build-arg SKIPTESTS=false 
	. 
