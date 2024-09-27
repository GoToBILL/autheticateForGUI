let currentBetPage = 0; // 현재 페이지 번호
const betPageSize = 12; // 한 페이지에 보여줄 내기 수

document.addEventListener('DOMContentLoaded', function () {
    // 게시판 바로가기 버튼 이벤트 설정
    document.getElementById('board-button').addEventListener('click', loadBoardPosts);
    // 내기 내역 바로가기 버튼 이벤트 설정
    document.getElementById('bet-board-button').addEventListener('click', loadBetBoardPosts);
});

// 내기 게시판 정보를 불러와 화면에 출력하는 함수
function loadBetBoardPosts() {
    axios.get('/bet-board/bets', {
        params: {
            page: currentBetPage,
            size: betPageSize
        }
    })
        .then(response => {
            const bets = response.data.content; // 서버에서 받은 내기 배열
            const totalBetPages = response.data.totalPages; // 전체 페이지 수

            // 내기 추가 버튼 HTML 생성
            let addBetButtonHtml = `
            <button id="add-bet-button" class="btn add-button">내기 추가</button>
        `;

            // 내기 목록 HTML 생성
            let betListHtml = '<div class="post-list">';
            bets.forEach(bet => {
                betListHtml += `
                <div class="post-card" data-bet-id="${bet.id}">
                    <h3 class="post-title">${bet.title}</h3>
                    <p class="post-content">${bet.description}</p>
                    <div class="post-footer">
                        <span class="post-author">참여자: ${bet.participants.join(', ')}</span>
                        <span class="post-date">종료일: ${bet.endDate}</span>
                    </div>
                </div>
            `;
            });
            betListHtml += '</div>';

            // 페이지 네이션 버튼 추가
            let betPaginationHtml = '<div class="pagination">';
            for (let i = 0; i < totalBetPages; i++) {
                betPaginationHtml += `<button class="page-button" onclick="goToBetPage(${i})">${i + 1}</button>`;
            }
            betPaginationHtml += '</div>';

            document.getElementById('main-content').innerHTML = addBetButtonHtml + betListHtml + betPaginationHtml;

            // 내기 추가 버튼 이벤트 추가
            document.getElementById('add-bet-button').addEventListener('click', showAddBetForm);

            // 내기 클릭 이벤트 추가
            document.querySelectorAll('.post-card').forEach(card => {
                card.addEventListener('click', function () {
                    const betId = this.dataset.betId;
                    showBetDetail(betId); // 내기 상세 정보 표시 함수 호출
                });
            });
        })
        .catch(error => {
            console.error('내기 내역을 불러오는 중 오류 발생:', error);
        });
}

// 내기 상세 정보 표시 함수
function showBetDetail(betId) {
    axios.get(`/bet-board/posts/${betId}`)
        .then(response => {
            const bet = response.data; // 서버에서 받은 내기 객체

            const betDetailHtml = `
                <div class="post-detail-container">
                    <h2 class="post-title">${bet.title}</h2>
                    <p class="post-content">${bet.description}</p>
                    <div class="post-footer">
                        <span class="post-author">참여자: ${bet.participants.join(', ')}</span>
                        <span class="post-date">종료일: ${bet.endDate}</span>
                    </div>
                    <button class="btn back-button" onclick="goBackToBetList()">목록으로 돌아가기</button>
                </div>
            `;
            document.getElementById('main-content').innerHTML = betDetailHtml;
        })
        .catch(error => {
            console.error('내기 상세 정보를 불러오는 중 오류 발생:', error);
        });
}

// 내기 게시판 페이지 전환 함수
function goToBetPage(pageNumber) {
    currentBetPage = pageNumber; // 현재 페이지 업데이트
    loadBetBoardPosts(); // 새로운 페이지의 내기 목록 로드
}

// 내기 목록으로 돌아가기 함수
function goBackToBetList() {
    loadBetBoardPosts(); // 내기 목록을 다시 불러옴
}

// 내기 추가 폼 표시 함수
function showAddBetForm() {
    const addBetHtml = `
        <div class="post-detail-container">
            <h2>새 내기 추가</h2>
            <form id="add-bet-form">
                <div style="width: 100%;">
                    <label for="bet-title">제목:</label>
                    <input type="text" id="bet-title" name="title" required>
                </div>
                <div style="width: 100%;">
                    <label for="bet-description">내기 내용:</label>
                    <textarea id="bet-description" name="description" required></textarea>
                </div>
                <div style="width: 100%;">
                    <label for="bet-participants">참여자 (쉼표로 구분):</label>
                    <input type="text" id="bet-participants" name="participants" required>
                </div>
                <div style="width: 100%;">
                    <label for="bet-endDate">종료일:</label>
                    <input type="date" id="bet-endDate" name="endDate" required>
                </div>
                <div class="button-container">
                    <button type="button" class="btn submit-button" onclick="addBet()">추가 완료</button>
                    <button type="button" class="btn cancel-button" onclick="goBackToBetList()">취소</button>
                </div>
            </form>
        </div>
    `;
    document.getElementById('main-content').innerHTML = addBetHtml;
}

// 새 내기 추가 함수
function addBet() {
    const title = document.getElementById('bet-title').value;
    const description = document.getElementById('bet-description').value;
    const participants = document.getElementById('bet-participants').value.split(',').map(p => p.trim());
    const endDate = document.getElementById('bet-endDate').value;

    axios.post('/bet-board/posts', { title, description, participants, endDate })
        .then(response => {
            console.log('내기 추가 성공:', response);
            goBackToBetList(); // 작성 후 목록으로 돌아가기
        })
        .catch(error => {
            console.error('내기 추가 중 오류 발생:', error);
        });
}
