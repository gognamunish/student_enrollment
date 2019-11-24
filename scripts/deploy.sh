
# Get latest code from master branch and build in current directory
git clone https://github.com/gognamunish/student_enrollment.git .
./mvnw clean package -DskipTests

# Create Docker Image
cd enrollment-api
docker build -t blackone/student_enrollment .

# Run Docker image
docker run -p 8080:8080 -t blackone/student_enrollment

