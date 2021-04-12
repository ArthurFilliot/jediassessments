import { createStore, combineReducers, applyMiddleware } from 'redux';
import { createAction,handleActions } from 'redux-actions';
import thunk from 'redux-thunk';

export const galacticStandardCalendarReducer = handleActions(
    {
        STORE_WINDOW: (state, action) => {
            state.galacticWindow = action.payload.window;
        },
        STORE_NOW: (state, action) => {
            let clientNow = action.payload.clientNow
            state.nowIndex = state.galacticWindow.dates.findIndex(entry => Math.abs(clientNow - entry.value) < 1000);
            if (state.nowIndex == -1) {
                state.nowIndex = state.galacticWindow.dates.length-1;
            }
            return state;
        }
    },
    { 
        galacticWindow: {},
        nowIndex: -1, 
    }
);
export const storeWindow = createAction('STORE_WINDOW');
export const storeNow = createAction('STORE_NOW');

const galacticStandardCalendarReducers = combineReducers({galacticStandardCalendar: galacticStandardCalendarReducer});
const galacticStandardCalendarStore = createStore(galacticStandardCalendarReducers, applyMiddleware(thunk));

export function updateFull(mGalacticWindow, clientNow) {
    galacticStandardCalendarStore.dispatch(() => {
        galacticStandardCalendarStore.dispatch(storeWindow({'window':mGalacticWindow}));
        galacticStandardCalendarStore.dispatch(storeNow({'clientNow':clientNow}));
    });
}

export function updatePartial(clientNow) {
    galacticStandardCalendarStore.dispatch(storeNow({'clientNow':clientNow}));
}