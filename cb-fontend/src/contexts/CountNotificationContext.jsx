import React, { createContext, useState, useContext } from 'react';

const CountNotificationContext = createContext(null);

export const CountNotificationProvider = ({ children }) => {
  const [countNotification, setCountNotification] = useState(null);

  return (
    <CountNotificationContext.Provider value={{ countNotification, setCountNotification }}>
      {children}
    </CountNotificationContext.Provider>
  );
};
  
export const useCountNotification = () => {
  return useContext(CountNotificationContext);
};
