## Documentation for Task 5

### Objective

Configure Nginx on the `web01` server to act as a reverse proxy and load balancer for two Spring Boot application instances.

### Configuration

Created an upstream block:

```nginx
upstream account_service {
    server app01:8080;
    server app02:8080;
}
```

Configured the server block:

```nginx
server {
    listen 80;

    location / {
        proxy_pass http://account_service;

        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

### Validation

Verified configuration syntax:

```bash
sudo nginx -t
```

Reloaded Nginx:

```bash
sudo systemctl restart nginx
```

Tested through the reverse proxy:

```bash
curl http://localhost/accounts
curl http://web01/accounts
```

### Result

* Nginx successfully accepted client requests on port **80**.
* Requests were forwarded to the Spring Boot application servers.
* The application returned the expected JSON responses.
* The reverse proxy was successfully placed in front of the backend services.

### Outcome

Task 5 was completed successfully. The application now follows a standard production architecture where clients communicate only with the Nginx server, while backend application servers remain behind the reverse proxy. Nginx is also configured to distribute requests between `app01` and `app02`, providing load balancing and a single entry point for the application.
