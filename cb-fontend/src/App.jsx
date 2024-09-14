import ChatComponent from "./components/ChatComponent"
import LogginComponent from "./components/LogginComponent"
import {BrowserRouter, Routes, Route} from "react-router-dom"
function App() {
  return (
    <>
    <BrowserRouter>
      <Routes>
          <Route path="/" element = { <LogginComponent/> }></Route>
          <Route path="/login" element = { <LogginComponent/> }></Route>
          <Route path="/chatbox" element = { <ChatComponent/> }></Route>
      </Routes>
    </BrowserRouter>
    </>
  )
}
export default App