import { createStore, combineReducers, applyMiddleware } from 'redux';
import { createAction,handleActions } from 'redux-actions';

export const galacticStandardCalendarReducer = handleActions(
    {
        STORE_NOW: (state, action) => {
            if (action.payload.running) {
                state.running = action.payload.running;
            }
            if (state.running) {
                let clientNow = action.payload.clientNow
                let mGalacticWindow = (action.payload.window) ? action.payload.window : state.galacticWindow;
                state.nowIndex = mGalacticWindow.dates.findIndex(entry => Math.abs(clientNow - entry.value) < 1000);
                if (state.nowIndex == -1) {
                    state.nowIndex = state.galacticWindow.dates.length-1;
                }
                state.galacticWindow = mGalacticWindow;
            }
            return state;
        },
        START: (state, action) => {
            state.running = true;
        },
        STOP: (state, action) => {
            state.running = false;
        } 
    },
    { 
        galacticWindow: {},
        nowIndex: -1, 
        running: false,
    }
);
export const storeWindow = createAction('STORE_WINDOW');
export const storeNow = createAction('STORE_NOW');
export const storeStart = createAction('START');
export const storeStop = createAction('STOP');

const galacticStandardCalendarReducers = combineReducers({galacticStandardCalendar: galacticStandardCalendarReducer});
const galacticStandardCalendarStore = createStore(galacticStandardCalendarReducers);

export function update(clientNow, mGalacticWindow, start) {  
    let payload = {'clientNow':clientNow}
    if (mGalacticWindow) {
        payload.window = mGalacticWindow;
    }
    if (start===true) {
        payload.running = true;
    }
    galacticStandardCalendarStore.dispatch(storeNow(payload));
}

let timer = null;
export function start(clientNow) {
    clearInterval(timer);
    timer = setInterval(()=>{
        clientNow = clientNow+1000;
        update(clientNow)
    }, 1000);
    galacticStandardCalendarStore.dispatch(storeStart());
    update(clientNow);
}

export function stop() {
    clearInterval(timer);
    galacticStandardCalendarStore.dispatch(storeStop());
}