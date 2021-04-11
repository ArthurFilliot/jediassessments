/**
 * Arthur Filliot
 * 11/04/2021
 */
const sseMock = {
  responsesGen:undefined,
  eventSource:undefined,
}
class EventSourceMock{
  constructor(url){
    sseMock.eventSource = this;
  }
  finished = false;
  responsesGen = sseMock.responsesGen;
  onmessage = (event)=>console.log('not defined');
  onerror = (error)=>console.log('not defined');
  fire() {
    let evt = this.responsesGen.next().value;
    if (evt.type==='end') {
      this.onerror(evt);
    }else{
      this.onmessage(evt);
    }  
  }
  close() {
    console.log('closed');
  }
};
global.EventSource = EventSourceMock
export default sseMock;