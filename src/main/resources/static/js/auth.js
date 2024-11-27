// 새 액세스 토큰을 요청하는 함수
async function refreshAccessToken() {
    const refreshToken = localStorage.getItem('refreshToken');
    if (!refreshToken) throw new Error('리프레시 토큰이 없습니다.');

    const response = await fetch('/auth/refresh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${refreshToken}`
        }
    });

    if (!response.ok) {
        throw new Error('토큰 갱신 실패');
    }

    const data = await response.json();
    storeTokens(data.accessToken, data.refreshToken);
    return data.accessToken;
}

// JWT 토큰을 Authorization 헤더에 추가하는 fetch 함수
async function authorizedFetch(url, options = {}) {
    let token = localStorage.getItem('accessToken');
    if (!token) {
        throw new Error('액세스 토큰이 없습니다.');
    }

    options.headers = {
        ...options.headers,
        'Authorization': `Bearer ${token}`
    };

    let response = await fetch(url, options);

    // 액세스 토큰이 만료된 경우
    if (response.status === 401) {
        try {
            token = await refreshAccessToken(); // 새로운 액세스 토큰 발급
            options.headers['Authorization'] = `Bearer ${token}`;
            response = await fetch(url, options); // 요청 재시도
        } catch (error) {
            console.error('토큰 갱신 실패:', error);
            throw error;
        }
    }

    return response;
}
