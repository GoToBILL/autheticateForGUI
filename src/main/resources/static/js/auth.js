// src/main/resources/static/js/auth.js

// JWT 토큰을 localStorage에 저장하는 함수
function storeToken(token) {
    localStorage.setItem('token', token);
    console.log('토큰 저장 완료:', token);
}


// JWT 토큰을 Authorization 헤더에 추가하는 fetch 함수
function authorizedFetch(url, options = {}) {
    const token = localStorage.getItem('token');
    if (token) {
        options.headers = {
            ...options.headers,
            'Authorization': `Bearer ${token}`
        };
    }
    return fetch(url, options);
}
