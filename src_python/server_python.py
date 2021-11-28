# 소켓 통신을 위해 socket import
import socket, threading;

# 서버에서 승인되면 데이터를 echo로 재송신함
def binder(client_socket, addr):
    # 접속 주소 알려줌
    print('Connected by', addr)
    f = open("test.txt", 'a+', encoding="UTF-8")
    try:
        while True:
            # 4byte 대기
            data = client_socket.recv(4);
            length = int.from_bytes(data, "little");
            # 데이터 수신
            data = client_socket.recv(length)
            msg = data.decode()
            # 받은 데이터 출력
            print(msg, end='')
            f.write(msg)
            msg = "echo : " + msg
            data = msg.encode()
            # 데이터 사이즈 구함
            length = len(data)
            # 데이터 전송
            client_socket.sendall(length.to_bytes(4, byteorder='little'))
            client_socket.sendall(data);
            if '이동' in msg:
                f.seek(0)
                text = f.read()
                print(text)
                print("이동하겠습니다.")
                f.truncate(0)
            elif '보여줘' in msg:
                f.seek(0)
                text = f.read()
                print(text)
                print("영상을 보여드리겠습니다.")
                f.truncate(0)
            elif '리셋' in msg:
                f.seek(0)
                text = f.read()
                print(text)
                print("리셋 성공")
                f.truncate(0)
            elif '돌아가' in msg:
                f.seek(0)
                text = f.read()
                print(text)
                print("처음 위치로 돌아가기 성공")
                f.truncate(0)
    except:
        # 접속 끊김
        print("except : ", addr)
    finally:
        # 접속 끊기
        client_socket.close()

# 소켓 생성
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 소켓 레벨, 데이터 형태 설정
server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
# 빈 포트 사용
server_socket.bind(('', 9999))
server_socket.listen()
try:
    while True:
        # 접속시 accept 발생
        # 소켓과 주소 튜플로 받음
        client_socket, addr = server_socket.accept()
        th = threading.Thread(target=binder, args=(client_socket, addr))
        th.start()
except:
    print("server")
finally:
    # 소켓 종료
    server_socket.close()
