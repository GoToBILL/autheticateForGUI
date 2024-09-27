let currentPage = 0; // 현재 페이지 번호
const pageSize = 12; // 한 페이지에 보여줄 게시글 수

document.addEventListener('DOMContentLoaded', function () {
    // 게시판 바로가기 버튼 이벤트 설정
    document.getElementById('board-button').addEventListener('click', loadBoardPosts);
});

// 게시판 정보를 불러와 화면에 출력하는 함수
function loadBoardPosts() {
    axios.get('/board/posts', {
        params: {
            page: currentPage,
            size:pageSize
        }
    })
        .then(response => {
            const posts = response.data.content; // 서버에서 받은 게시글 배열
            const totalPages = response.data.totalPages; // 전체 페이지 수

            // 글 추가 버튼 HTML 생성
            let addButtonHtml = `
            <button id="add-post-button" class="btn add-button">글 추가</button>
        `;

            // 게시글 목록 HTML 생성
            let postListHtml = '<div class="post-list">';
            posts.forEach(post => {
                postListHtml += `
                <div class="post-card" data-post-id="${post.id}">
                    <h3 class="post-title">${post.title}</h3>
                    <p class="post-content">${post.content}</p>
                    <div class="post-footer">
                        <span class="post-author">작성자: ${post.author}</span>
                        <span class="post-date">작성일: ${post.createdDate}</span>
                    </div>
                </div>
            `;
            });
            postListHtml += '</div>';

            // 페이지 네이션 버튼 추가
            let paginationHtml = '<div class="pagination">';
            for (let i = 0; i < totalPages; i++) {
                paginationHtml += `<button class="page-button" onclick="goToPage(${i})">${i + 1}</button>`;
            }
            paginationHtml += '</div>';

            document.getElementById('main-content').innerHTML = addButtonHtml + postListHtml + paginationHtml;

            // 글 추가 버튼 이벤트 추가
            document.getElementById('add-post-button').addEventListener('click', showAddPostForm);

            // 게시글 클릭 이벤트 추가
            document.querySelectorAll('.post-card').forEach(card => {
                card.addEventListener('click', function () {
                    const postId = this.dataset.postId;
                    showPostDetail(postId); // 게시글 상세 정보 표시 함수 호출
                });
            });
        })
        .catch(error => {
            console.error('게시글을 불러오는 중 오류 발생:', error);
        });
}

function showPostDetail(postId) {
    axios.get(`/board/posts/${postId}`)
        .then(response => {
            const post = response.data; // 서버에서 받은 게시글 객체

            const postDetailHtml = `
                <div class="post-detail-container">
                    <h2 class="post-title">${post.title}</h2>
                    <p class="post-content">${post.content}</p>
                    <div class="post-footer">
                        <span class="post-author">작성자: ${post.author}</span>
                        <span class="post-date">작성일: ${post.createdDate}</span>
                    </div>
                    <button class="btn back-button" onclick="goBackToList()">목록으로 돌아가기</button>
                </div>
            `;
            document.getElementById('main-content').innerHTML = postDetailHtml;
        })
        .catch(error => {
            console.error('게시글 상세 정보를 불러오는 중 오류 발생:', error);
        });
}

// 페이지 전환 함수
function goToPage(pageNumber) {
    currentPage = pageNumber; // 현재 페이지 업데이트
    loadBoardPosts(); // 새로운 페이지의 게시글 로드
}
// 목록으로 돌아가기 함수
function goBackToList() {
    loadBoardPosts(); // 게시판 목록을 다시 불러옴
}

// 글 추가 폼 표시 함수
function showAddPostForm() {
    const addPostHtml = `
        <div class="post-detail-container">
            <h2>새 글 작성</h2>
            <form id="add-post-form">
                <div style="width: 100%;">
                    <label for="post-title">제목:</label>
                    <input type="text" id="post-title" name="title" required>
                </div>
                <div style="width: 100%;">
                    <label for="post-content">내용:</label>
                    <textarea id="post-content" name="content" required></textarea>
                </div>
<!--                <div style="width: 100%;">-->
<!--                    <label for="post-author">작성자:</label>-->
<!--                    <input type="text" id="post-author" name="author" required>-->
<!--                </div>-->
                <div class="button-container">
                    <button type="button" class="btn submit-button" onclick="addPost()">작성 완료</button>
                    <button type="button" class="btn cancel-button" onclick="goBackToList()">취소</button>
                </div>
            </form>
        </div>
    `;
    document.getElementById('main-content').innerHTML = addPostHtml;
}

// 새 게시글 추가 함수
function addPost() {
    const title = document.getElementById('post-title').value;
    const content = document.getElementById('post-content').value;
    // const author = document.getElementById('post-author').value;

    axios.post('/board/posts', { title, content})
        .then(response => {
            console.log('게시글 추가 성공:', response);
            goBackToList(); // 작성 후 목록으로 돌아가기
        })
        .catch(error => {
            console.error('게시글 추가 중 오류 발생:', error);
        });
}