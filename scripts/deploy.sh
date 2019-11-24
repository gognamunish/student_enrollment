echo "Getting latest code from Github into folder: $PWD"

# Get Code
git clone https://github.com/gognamunish/student_enrollment.git
cd student_enrollment/enrollment-api
ls -lart

# Build Code
./mvnw clean package -DskipTests

# Create Docker Image
docker build -t taoyogi/student_enrollment .

# Run Docker image
docker run -p 8080:8080 -t taoyoi/student_enrollment

