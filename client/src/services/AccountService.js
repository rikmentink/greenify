async function handleErrorMessages(response) {
    if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message);
    }
}


async function login (email, password) {
    const response = await fetch('http://localhost:8080/api/login', {
        method: 'POST',
        body: JSON.stringify({ email, password })
    });

    await handleErrorMessages(response);

    let token = response.headers.get('Authorization')
    token = token.replace('Bearer ', '');

    return token
}

async function register (email, password, firstName, lastName){
    const response = await fetch('http://localhost:8080/api/account/register', {
        method: 'POST',
        body: JSON.stringify({ email, password, firstName, lastName}),
        headers: {
            'Content-Type': 'application/json'
        }
    });

    await handleErrorMessages(response);
}

export { login, register };