test:
	docker-compose up -d
	./gradlew test:test
	docker-compose down
