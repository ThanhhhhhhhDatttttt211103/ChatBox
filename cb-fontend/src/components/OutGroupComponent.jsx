import { forwardRef } from "react";
import { useStompClient } from "../contexts/StomppClientContext";
import { useUser } from "../contexts/UserContext";
import { saveGroupDetail } from "../services/GroupService";
import { useChatRoom } from "../contexts/ChatRoomContext";

const OutGroupComponent = forwardRef(({setOpenOutGroup, setMessageRequest, idGroup},  ref) => {
    const { stompClient } = useStompClient();
    const { user } = useUser();
    const { setChatRoom } = useChatRoom();

    function sendNotifi(content) {
        const chatMessage = {
          sender: user.id,
          received: idGroup,
          content: content,
          code: idGroup,
          sendTime: new Date(),
          isEnable: 1,
          type: 4,
          messageEdt: null,
          senderName: user.fullname
      };
      stompClient.send("/app/chat", {}, JSON.stringify(chatMessage));
      }

    async function handleOutGroup() {
        const groupDetail = {"idGroup": idGroup, "idUser": user.id, "isEnable":0};
        await saveGroupDetail(groupDetail)
        sendNotifi(user.fullname + " has left this conversation"),
        outGroupUI();
    }

    function outGroupUI() {
        const hearderChat = document.getElementById('hearder-chat');
        hearderChat.classList.add('hidden')
        const typeMessage = document.getElementById('messageForm');
        typeMessage.classList.add('hidden');

        const listChatRoom = document.getElementById("connectedUsers");
        const chatRoom = listChatRoom.querySelector(`#${idGroup}`);
        listChatRoom.removeChild(chatRoom);
        setOpenOutGroup(false);
        setMessageRequest("");
        setChatRoom("");
    }

  return (
    <div className='addToGroup-bg' ref={ref}> 
        <div className='outGroup-container'>
            <div className="titleCloseBtn">
                <span>Leave conversation</span>
                <button onClick={() => { setOpenOutGroup(false);}}> &times; </button>
            </div>
            <div className='body'>
                <div className='content-body'>
                    <span>
                        Are you sure you want to leave this conversation? This conversation will be cleared for you
                        and you will no longer be a member of this conversation
                    </span>
                </div>
            </div>
            <div className='footer'>
                <div className="button-out-group" onClick={handleOutGroup}>
                    <button style={{ fontWeight: 650 }}>Confirm</button>
                </div>
                <div className="button-out-group" onClick={() => { setOpenOutGroup(false);}}>
                    <button>Cancel</button>
                </div>
            </div>
        </div>
    </div>
  );
});

export default OutGroupComponent;