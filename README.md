# triple

# 사용

## h2console 접속
jdbc:h2:mem:testdb

## /events JSON 요청
{
"type": "REVIEW",
"action": "ADD",
"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
"content": "좋아요!",
"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-
851d-4a50-bb07-9cc15cbdc332"],
"userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
"placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}

{
"type": "REVIEW",
"action": "MOD",
"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
"content": "asd",
"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-
851d-4a50-bb07-9cc15cbdc332"],
"userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
"placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}

{
"type": "REVIEW",
"action": "DELETE",
"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
"content": "asd",
"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-
851d-4a50-bb07-9cc15cbdc332"],
"userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
"placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}

## 테스트
![image](https://user-images.githubusercontent.com/62234293/178147632-2556a960-180d-4554-9bcc-218bf4c3b91c.png)



