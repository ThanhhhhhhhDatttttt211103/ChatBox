import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import 'bootstrap/dist/css/bootstrap.min.css'
import './ChatBox.css';
import { AccountProvider } from './contexts/AccountContext.jsx';
import { StompClientProvider } from './contexts/StomppClientContext.jsx';
import { ChatRoomProvider } from './contexts/ChatRoomContext.jsx';
import { UserProvider } from './contexts/UserContext.jsx';
import { PrevMessageProvider } from './contexts/PrevMessageContext.jsx';
import { CountNotificationProvider } from './contexts/CountNotificationContext.jsx';
import { MessageRequestProvider } from './contexts/MessageRequestContext.jsx';
import { ListChatRoomProvider } from './contexts/ListChatRoomContext.jsx';

ReactDOM.createRoot(document.getElementById('root')).render(
  // <React.StrictMode>
    <AccountProvider>
      <UserProvider>
        <StompClientProvider>
        <ListChatRoomProvider>
          <ChatRoomProvider>
            <PrevMessageProvider>
              <CountNotificationProvider> 
                <MessageRequestProvider>
                  <App />
                </MessageRequestProvider>
              </CountNotificationProvider>
            </PrevMessageProvider>
          </ChatRoomProvider>
        </ListChatRoomProvider>
        </StompClientProvider>
      </UserProvider>
    </AccountProvider>
  // </React.StrictMode>,
)
