
async function login (email, password) {
    const response = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        body: JSON.stringify({ email, password })
    }).then((response) => {
        console.log(response.headers);
    });
    if (!response.ok) {
        throw new Error(`Failed to login: ${response.statusText}`);
    }
    return response.json();
}

export { login };