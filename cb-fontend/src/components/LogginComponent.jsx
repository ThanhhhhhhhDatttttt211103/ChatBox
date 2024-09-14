import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { login } from '../services/AccountService'
import { useAccount } from '../contexts/AccountContext'
import { useUser } from '../contexts/UserContext';
import { findUser } from '../services/UserService';

var accountData = {};
const LogginComponent = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const { setAccount } = useAccount();
  const { setUser } = useUser();
  const navigator = useNavigate();

  function handleLogin(e) {
    e.preventDefault();
    const account = { username, password };
    login(account).then(response => { 
      const accountData = response.data;
      if (accountData) {
        setAccount(accountData);
        return findUser(accountData.idUser).then(response => {
          setUser(response.data);
          console.log(response.data);
          navigator('/chatbox');
        });
      }
    });
  }
  

  return (
    <div className="user-form content-center" id="username-page">
        <h2>Enter Chatroom</h2>
        <form id="usernameForm">
            <label htmlFor="username">Username:</label>
            <input type="text" id="username" name="username" value={username} onChange={(e) => setUsername(e.target.value)} required />

            <label htmlFor="password">Password:</label>
            <input type="password" id="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} required />

            <button type="submit" onClick={handleLogin}>Enter Chatroom</button>
        </form>
    </div>
  )
}

export default LogginComponent