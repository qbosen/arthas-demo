import socket

import iterm2

async def main(connection):
    app = await iterm2.async_get_app(connection)
    window = app.current_window
    while True:
        try:
            client, addr = server.accept()
        except KeyboardInterrupt:
            return
        session = window.current_tab.current_session
        command = client.recv(1024).decode()
        print(f'执行: {command}')
        await session.async_send_text(command + "\n")
        client.close()

if __name__ == '__main__':
    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.bind(('localhost', 8999))
    server.listen(5)
    iterm2.run_until_complete(main)
    server.close()
