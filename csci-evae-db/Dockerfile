# Use an Oracle Database image as base image
FROM gvenzl/oracle-free

# Set the working directory inside the container
WORKDIR /database

# Copy the entire folder containing your scripts to the container
COPY . .

# Set the environment variable for the Oracle password
ENV ORACLE_PASSWORD=dosi

# Expose Oracle listener port
EXPOSE 1521

# Create a volume for Oracle data
VOLUME /opt/oracle/oradata