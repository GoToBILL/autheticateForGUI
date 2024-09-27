// common.js
document.addEventListener('DOMContentLoaded', function () {
    const homeLink = document.getElementById('home-link');
    if (homeLink) {
        homeLink.addEventListener('click', function () {
            window.location.href = '/';
        });
    }

    // 로그인 상태 확인 및 토큰 유효성 검증
    const token = localStorage.getItem('token');
    if (token) {
        // 토큰이 존재하면 서버로 유효성 검사 요청
        axios.get('/validate-token', {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                if (response.data.valid) {
                    // 토큰이 유효한 경우
                    document.getElementById('login-btn').style.display = 'none';
                    document.getElementById('logout-btn').style.display = 'inline';
                } else {
                    // 토큰이 유효하지 않은 경우
                    document.getElementById('login-btn').style.display = 'inline';
                    document.getElementById('logout-btn').style.display = 'none';
                    localStorage.removeItem('token'); // 유효하지 않은 토큰 삭제
                }
            })
            .catch(error => {
                console.error('Token validation failed:', error);
                document.getElementById('login-btn').style.display = 'inline';
                document.getElementById('logout-btn').style.display = 'none';
                localStorage.removeItem('token'); // 에러 발생 시 토큰 삭제
            });
    } else {
        // 토큰이 없는 경우
        document.getElementById('login-btn').style.display = 'inline';
        document.getElementById('logout-btn').style.display = 'none';
    }
});

// 로그아웃 함수
function logout() {
    localStorage.removeItem('token'); // 토큰 삭제
    window.location.href = '/'; // 로그아웃 후 홈으로 이동
}
