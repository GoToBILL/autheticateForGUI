// Axios 인터셉터 설정
window.axios = axios;
axios.defaults.headers.post['Content-Type'] = 'application/json';

axios.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token');
        if (token) {
            // token 값에 문제가 있을 경우 인코딩하여 처리
            config.headers['Authorization'] = `Bearer ${token}`;
            return config;
        }
        alert('로그인이 필요한 서비스입니다.');
        return config;
    },
    error => {
        return Promise.reject(error);
    }
);