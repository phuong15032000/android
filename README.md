<style {#ele{fontColor: yellow;}}></style>
- ![#f03c15](https://placehold.it/15/f03c15/000000?text=+) `#f03c15`
# Giới thiệu về NoteApp
Đây là một ứng dụng sổ ghi chú trên hệ điều hành android, được chúng em hoàn thiện bằng ngôn ngữ java, python.
Một điểm nổi bật so với những ứng dụng sổ ghi chú đã có trên thị trường thì ứng dụng của chúng em ngoài việc có thể mở khoá bằng mật khẩu thì còn có thể mở khoá bằng gương mặt.<br/>
Chức năng:
<ul>
<li class="ele">Tạo mật khẩu cho lần đầu sử dụng</li>
<li class="ele">Mở khoá bằng mật khẩu</li>
<li class="ele">Mở khoá bằng gương mặt</li>
<li class="ele">Xem, thêm, sửa, xoá ghi chú</li>
<li class="ele">Thêm ảnh vào ghi chú</li>
</ul>
Một số hình ảnh demo:

1. Màn hình chính<br />
![demo1](https://user-images.githubusercontent.com/57005560/118626823-6425c700-b7f5-11eb-8ac5-8038c35f37a9.PNG)






2. Thêm ghi chú mới<br />
![demo2](https://user-images.githubusercontent.com/57005560/118627699-24131400-b7f6-11eb-81c4-da9f7a5e8785.PNG)






3. Xoá ghi chú<br />
![demo3](https://user-images.githubusercontent.com/57005560/118627841-40af4c00-b7f6-11eb-96e4-a58aae96806b.PNG)






4. Mở khoá bằng gương mặt<br />
![demo4](https://scontent-hkg4-1.xx.fbcdn.net/v/t1.15752-9/201349062_829360584684816_3120177940044159575_n.png?_nc_cat=101&ccb=1-3&_nc_sid=ae9488&_nc_ohc=jkh6QqUHSC4AX81U1Mr&_nc_ht=scontent-hkg4-1.xx&oh=1e3ede8002d2792501c381b8f8797658&oe=60D4A4B9)





5. Mở khoá bằng mật khẩu<br />
![demo5](https://scontent-hkg4-1.xx.fbcdn.net/v/t1.15752-9/201173764_199118592101986_1144722416540302003_n.png?_nc_cat=101&ccb=1-3&_nc_sid=ae9488&_nc_ohc=uUiQKz31dmwAX9AhXmM&tn=argkVnDAia8JK_2p&_nc_ht=scontent-hkg4-1.xx&oh=256e95b4efa2c75f80160117c7dca7df&oe=60D515A5)






<b> Hướng dẫn cài đặt </b>
1) Clone 2 project là NoteApp và Facenet_android về máy
2) Trong Facenet_android, mở file Server.java, sửa biến masterPath thành tên đường dẫn lưu project Facenet_android
3) Trong Facenet_android, mở file src/android.py, sửa biến MASTER_PATH thành tên đường dẫn lưu project Facenet_android
4) Trong Facenet_android, chạy file Server.java, đây là server dùng để nhận ảnh từ camera điện thoại đến server để nhận dạng gương mặt
5) Trong Facenet_android, chạy file src/android.py, tại đây sẽ đọc ảnh từ folder vừa nhận ảnh về để nhận dạng gương mặt